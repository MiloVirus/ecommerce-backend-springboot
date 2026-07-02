package com.ecommerce.backend.products.infrastructure.dtos;

import java.util.List;
import java.util.UUID;

public record OrderCreatedEventDTO(
    UUID orderId,
    Long customerId,
    List<OrderItemEventDTO> items
) {}