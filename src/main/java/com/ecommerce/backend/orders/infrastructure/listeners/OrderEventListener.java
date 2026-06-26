package com.ecommerce.backend.orders.infrastructure.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderEventListener {

    private final ObjectMapper objectMapper;


    public OrderEventListener(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

      @KafkaListener(topics = "order-commands", groupId= "ecommerce-group")
      public void listenOrderEvents(String message)
      {
            System.out.println("\n=================================================");
            System.out.println("🔥 ¡MENSAJE RECIBIDO DESDE KAFKA! 🔥");
            System.out.println("Payload: " + message);
            System.out.println("=================================================\n");
      }
}
