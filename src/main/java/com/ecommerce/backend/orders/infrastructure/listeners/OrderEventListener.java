package com.ecommerce.backend.orders.infrastructure.listeners;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.orders.application.services.OrderService;
import com.ecommerce.backend.orders.domain.models.OrderStatus;
import com.ecommerce.backend.orders.infrastructure.dtos.ProductResponseEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderEventListener {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;


    public OrderEventListener(ObjectMapper objectMapper, OrderService orderService)
    {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

      @KafkaListener(topics = "order-commands", groupId= "ecommerce-group")
      public void listenOrderEvents(String message)
      {
            System.out.println("\n=================================================");
            System.out.println("🔥 ¡MENSAJE RECIBIDO DESDE KAFKA! 🔥");
            System.out.println("Payload: " + message);
            System.out.println("=================================================\n");
      }

      @KafkaListener(topics = "product-events", groupId = "orders-clean-group")
      public void listenProductEvents(String message) {
        try {
        
            ProductResponseEventDTO responseDto = objectMapper.readValue(message, ProductResponseEventDTO.class);
            

            UUID orderId = UUID.fromString(responseDto.orderId());
            String status = responseDto.status();

            if ("APPROVED".equals(status)) {
                orderService.updateOrderStatus(orderId, OrderStatus.APPROVED); 
                System.out.println("🎉 [Orders] Order " + orderId + " has been APPROVED!");
            } else {
                orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED); 
                System.out.println("❌ [Orders] Order " + orderId + " has been REJECTED.");
            }

        } catch (Exception e) {
            System.err.println("❌ Error processing products response: " + e.getMessage());
        }
}
}
