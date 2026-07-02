package com.ecommerce.backend.products.infrastructure.dtos;

import java.math.BigDecimal;

public record OrderItemEventDTO(
    Long productId,
    Integer quantity,
    BigDecimal priceAtPurchase 
) {}