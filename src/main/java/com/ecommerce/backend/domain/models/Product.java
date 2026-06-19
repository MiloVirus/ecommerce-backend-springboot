package com.ecommerce.backend.domain.models;
import java.math.BigDecimal;

public record Product (
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    boolean isActive
)
{
    
}
