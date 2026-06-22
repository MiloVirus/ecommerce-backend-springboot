package com.ecommerce.backend.orders.domain.models;

import java.math.BigDecimal;

public record OrderItem(
    Long productId,
    Long quantity,
    BigDecimal priceAtPurchase
) {

}
