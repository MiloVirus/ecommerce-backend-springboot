package com.ecommerce.backend.orders.infrastructure.dtos;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderResponseDTO(
    @NotNull(message = "El ID de la orden no puede ser nulo") 
    UUID orderId,

    @NotNull(message = "El ID del cliente no puede ser nulo") 
    Long customerId,

    @PositiveOrZero(message = "El monto total no puede ser negativo") 
    BigDecimal totalAmount,

    @NotNull(message = "El estado de la orden no puede ser nulo") 
    String status,

    @NotNull(message = "La lista de ítems no puede ser nula")
    @NotEmpty(message = "La lista de ítems no puede estar vacía")
    @Valid 
    List<OrderItemResponse> items
) {}