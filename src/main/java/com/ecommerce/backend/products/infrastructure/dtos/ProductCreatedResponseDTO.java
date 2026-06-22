package com.ecommerce.backend.products.infrastructure.dtos;

import java.math.BigDecimal;

public record ProductCreatedResponseDTO(
    Long id,
    String name,
    String description,
    BigDecimal price
) {

}
