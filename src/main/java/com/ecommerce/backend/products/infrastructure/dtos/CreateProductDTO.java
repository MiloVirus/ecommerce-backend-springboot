package com.ecommerce.backend.products.infrastructure.dtos;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateProductDTO(

    @NotBlank(message = "Product name can't be empty")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    String name,

    @NotBlank(message="Description field is mandatory")
    String description,

    @NotNull(message="Product price is mandatory")
    @Positive(message="Product price must be positive")
    BigDecimal price,
    
    @NotNull(message="Product stock is mandatory")
    @Positive(message="Product stock must be greater than 0")
    Integer stock


   
)
    {

    
    
}
