package com.ecommerce.backend.orders.infrastructure.dtos;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderDTO(

    @NotNull
    @Positive
    Long productId,

    @NotNull
    @Positive
    Long customerId,

    List<CreateOrderItemRequest> orderItems,

    @NotNull
    @Positive
    Long quantity,

    @NotNull
    @Positive
    BigDecimal totalAmountBigDecimal
) {
}
