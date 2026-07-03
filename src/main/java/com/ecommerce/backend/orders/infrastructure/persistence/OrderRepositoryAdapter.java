package com.ecommerce.backend.orders.infrastructure.persistence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderStatus;
import com.ecommerce.backend.orders.domain.repositories.OrderRepository;
import com.ecommerce.backend.products.infrastructure.dtos.OrderCreatedEventDTO;
import com.ecommerce.backend.products.infrastructure.dtos.OrderItemEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class OrderRepositoryAdapter implements OrderRepository{

    private final JpaOrderRepository jpaOrderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper)
    {
        this.jpaOrderRepository = jpaOrderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
public Order saveOrder(Order order)
{
    OrderEntity entity = OrderEntity.fromDomain(order);
    OrderEntity savedOrder = jpaOrderRepository.save(entity);
    Order convertedOrder = savedOrder.toDomain();
    
    
    if (order.status() == com.ecommerce.backend.orders.domain.models.OrderStatus.PENDING) {
        try {
            List<OrderItemEventDTO> itemsEvent = savedOrder.getItems().stream()
                .map(item -> new OrderItemEventDTO(
                    item.getProductId(), 
                    item.getQuantity().intValue(),
                    item.getPriceAtPurchase()
                ))
                .toList();

            OrderCreatedEventDTO eventDto = new OrderCreatedEventDTO(
                savedOrder.getId(),       
                savedOrder.getCustomerId(), 
                itemsEvent                 
            );

   
            String jsonMessage = objectMapper.writeValueAsString(eventDto);

       
            kafkaTemplate.send("orders-events", jsonMessage);
            System.out.println("🚀 [Orders] Evento enviado a Kafka con éxito para la orden: " + savedOrder.getId());

        } catch (Exception e) {
            System.err.println("❌ [Orders] Error al serializar el evento de la orden: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("🏁 [Orders] Guardado en Base de Datos. SAGA finalizada con éxito. Estado: " + order.status());
    }

    return convertedOrder;
}

    @Override
    public Optional<Order> findById(UUID id)
    {
        return jpaOrderRepository.findByIdWithItems(id)
        .map(OrderEntity::toDomain);
    }

    @Override
    public List<Order> findAllOrders()
    {
        return jpaOrderRepository.findAll()
        .stream()
        .map(OrderEntity::toDomain)
        .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(OrderStatus orderStatus)
    {
        return jpaOrderRepository.findByStatus(orderStatus) 
            .stream()                                   
            .map(OrderEntity::toDomain)
            .toList();
    }

}
