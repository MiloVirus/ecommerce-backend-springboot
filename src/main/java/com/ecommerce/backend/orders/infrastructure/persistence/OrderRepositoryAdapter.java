package com.ecommerce.backend.orders.infrastructure.persistence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderStatus;
import com.ecommerce.backend.orders.domain.repositories.OrderRepository;

public class OrderRepositoryAdapter implements OrderRepository{

    private final JpaOrderRepository jpaOrderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository, KafkaTemplate<String, String> kafkaTemplate)
    {
        this.jpaOrderRepository = jpaOrderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }



    @Override
    public Order saveOrder(Order order)
    {
        OrderEntity entity = OrderEntity.fromDomain(order);
        OrderEntity savedOrder = jpaOrderRepository.save(entity);
        Order convertedOrder = savedOrder.toDomain();
        
        String messageEvent = "Producto Creado -> ID: " + savedOrder.getId();

        kafkaTemplate.send("orders-events", messageEvent);

        return convertedOrder;
    }

    @Override
    public Optional<Order> findById(UUID id)
    {
        return jpaOrderRepository.findById(id)
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
