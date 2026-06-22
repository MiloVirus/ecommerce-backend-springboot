package com.ecommerce.backend.products.infrastructure.dtos;

import java.math.BigDecimal;

public record ProductResponseDTO(
    Long id,
    String name,
    String description,
    BigDecimal price
) {

}
