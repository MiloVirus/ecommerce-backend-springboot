package com.ecommerce.backend.orders.infrastructure.mappers;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.infrastructure.dtos.OrderItemResponse;
import com.ecommerce.backend.orders.infrastructure.dtos.OrderResponseDTO;

@Component
public class OrderResponseMapper {
    public OrderResponseDTO toResponseDTO(Order order) {
        List<OrderItemResponse> itemResponses = order.items().stream()
                .map(item -> new OrderItemResponse(item.productId(), item.quantity(), item.priceAtPurchase()))
                .toList();

        return new OrderResponseDTO(order.id(), order.customerId(), order.totalAmount(), order.status().name(), itemResponses);
    }
}
