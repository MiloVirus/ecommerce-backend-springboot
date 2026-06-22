package com.ecommerce.backend.products.infrastructure.listeners;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.products.application.services.ProductService;
import com.ecommerce.backend.products.domain.models.Product;
import com.ecommerce.backend.products.infrastructure.dtos.CreateProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Component
public class ProductEventListener {
    private final ObjectMapper objectMapper;
    private final ProductService productService;
    private final Validator validator;
    

    public ProductEventListener(ProductService productService, ObjectMapper objectMapper, Validator validator)
    {
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.validator = validator;
        
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
}
