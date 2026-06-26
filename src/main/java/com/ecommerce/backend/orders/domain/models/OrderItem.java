package com.ecommerce.backend.orders.domain.models;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(
    UUID id,
    Long productId,
    Long quantity,
    BigDecimal priceAtPurchase
) {

}
