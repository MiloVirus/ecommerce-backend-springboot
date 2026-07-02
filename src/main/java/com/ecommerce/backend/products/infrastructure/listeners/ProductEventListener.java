package com.ecommerce.backend.products.infrastructure.listeners;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.products.application.services.ProductService;
import com.ecommerce.backend.products.domain.models.Product;
import com.ecommerce.backend.products.infrastructure.dtos.CreateProductDTO;
import com.ecommerce.backend.products.infrastructure.dtos.OrderCreatedEventDTO;
import com.ecommerce.backend.products.infrastructure.dtos.OrderItemEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Component
public class ProductEventListener {
    private final ObjectMapper objectMapper;
    private final ProductService productService;
    private final Validator validator;
    private final KafkaTemplate<String, String> kafkaTemplate;
    

    public ProductEventListener(ProductService productService, ObjectMapper objectMapper, Validator validator, KafkaTemplate kafkaTemplate)
    {
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.kafkaTemplate = kafkaTemplate;
        
    }

    @KafkaListener(topics = "product-commands", groupId= "ecommerce-group")
    public void listenProductEvents(String message)
    {
        try {
            CreateProductDTO dto = objectMapper.readValue(message, CreateProductDTO.class);

            Set<ConstraintViolation<CreateProductDTO>> violations = validator.validate(dto);

            if (!violations.isEmpty()) {
              
                violations.forEach(v -> System.err.println("❌ Error in field: " + v.getMessage()));
                return; 
            }

            Product domainProduct = new Product(
                null,
                dto.name(),
                dto.description(),
                dto.price(),
                dto.stock(),
                dto.isActive()
            );

            productService.createProduct(domainProduct);

            System.out.println("\n=================================================");
            System.out.println("🔥 ¡MENSAJE RECIBIDO DESDE KAFKA! 🔥");
            System.out.println("Payload: " + message);
            System.out.println("=================================================\n");

        } catch (Exception e) {
            System.err.println("❌ Error de casteo de JSON: " + e.getMessage());
            System.err.println("❌ ERROR EN EL LISTENER:");
            e.printStackTrace();
        } 
    }

@KafkaListener(topics = "orders-events", groupId = "productos-final-group")
public void listenOrderCreatedEvents(String message) {
    System.out.println("📥 ¡ALGO LLEGÓ A PRODUCTOS DESDE KAFKA!: " + message);
    OrderCreatedEventDTO orderDto = null;
    try {
        
         orderDto = objectMapper.readValue(message, OrderCreatedEventDTO.class);
        
        System.out.println("\n📦 [Products] Procesando stock para la Orden ID: " + orderDto.orderId());

       
        for (OrderItemEventDTO item : orderDto.items()) {
            
            productService.reduceStock(Long.valueOf(item.productId()), Integer.valueOf(item.quantity()));
            
            System.out.println("✅ Stock reducido para Producto ID: " + item.productId() + " | Cantidad: " + item.quantity());
        }

        String successResponse = String.format(
            "{\"orderId\":\"%s\", \"status\":\"APPROVED\", \"reason\":\"Stock reservado con éxito\"}", 
            orderDto.orderId()
        );

        kafkaTemplate.send("product-events", successResponse);

    } catch (Exception e) {
        System.err.println("❌ ERROR AL PROCESAR STOCK EN EL SAGA: " + e.getMessage());
    
        if (orderDto != null) {
            String failureResponse = String.format(
                "{\"orderId\":\"%s\", \"status\":\"REJECTED\", \"reason\":\"%s\"}", 
                orderDto.orderId(), 
                e.getMessage().replace("\"", "\\\"")
            );
            
            kafkaTemplate.send("product-events", failureResponse);
}
    }
}
}
