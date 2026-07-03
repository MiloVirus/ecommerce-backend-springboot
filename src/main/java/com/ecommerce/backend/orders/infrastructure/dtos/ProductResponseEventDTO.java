package com.ecommerce.backend.orders.infrastructure.dtos;

public record ProductResponseEventDTO(
    String orderId,
    String status,
    String reason
) {}