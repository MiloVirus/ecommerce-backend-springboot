package com.ecommerce.backend.orders.infrastructure.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.orders.application.services.OrderService;
import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderItem;
import com.ecommerce.backend.orders.domain.models.OrderStatus;
import com.ecommerce.backend.orders.infrastructure.dtos.CreateOrderDTO;
import com.ecommerce.backend.orders.infrastructure.dtos.OrderItemResponse;
import com.ecommerce.backend.orders.infrastructure.dtos.OrderResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderDTO dto) {

        List<OrderItem> domainItems = dto.orderItems().stream()
                .map(itemDto -> new OrderItem(
                null,
                itemDto.productId(),
                itemDto.quantity(),
                itemDto.priceAtPurchase()
        ))
                .toList();

        BigDecimal totalAmount = domainItems.stream()
                .map(item -> item.priceAtPurchase().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order newOrder = new Order(
                null,
                dto.customerId(),
                OrderStatus.PENDING,
                domainItems,
                totalAmount
        );

        Order createdOrder = orderService.createOrder(newOrder);

        List<OrderItemResponse> itemResponses = createdOrder.items().stream()
                .map(item -> new OrderItemResponse(
                item.productId(),
                item.quantity(),
                item.priceAtPurchase()
        ))
                .toList();

        OrderResponseDTO response = new OrderResponseDTO(
                createdOrder.id(),
                createdOrder.customerId(),
                createdOrder.totalAmount(),
                createdOrder.status().name(),
                itemResponses
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
