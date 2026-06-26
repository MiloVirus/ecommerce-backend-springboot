package com.ecommerce.backend.orders.infrastructure.dtos;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemResponse(

    @NotNull
    Long productId,

    @NotNull
    @Positive
    Long quantity,

    @NotNull
    @Positive
    BigDecimal priceAtPurchase
) {}
