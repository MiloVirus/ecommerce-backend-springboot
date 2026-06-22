package com.ecommerce.backend.orders.domain.models;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record Order (
    UUID id,
    Long customerId,
    OrderStatus status,
    List<OrderItem> items,
    BigDecimal totalAmount
) {

}
