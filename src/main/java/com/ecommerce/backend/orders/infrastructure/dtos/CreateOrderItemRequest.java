package com.ecommerce.backend.orders.infrastructure.dtos;


import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record CreateOrderItemRequest(
    @NotNull
    Long productId,

    @NotNull
    Long quantity,

    @NotNull
    BigDecimal priceAtPurchase


) {

   

}
