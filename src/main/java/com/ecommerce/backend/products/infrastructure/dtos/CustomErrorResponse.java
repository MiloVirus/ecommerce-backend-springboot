package com.ecommerce.backend.products.infrastructure.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public record CustomErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    Map<String, String> errors // Aquí guardaremos: "campo" -> "mensaje de error"
) {}