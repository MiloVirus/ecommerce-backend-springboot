package com.ecommerce.backend.orders.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.orders.application.services.OrderService;
import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.infrastructure.dtos.CreateOrderDTO;
import com.ecommerce.backend.orders.infrastructure.dtos.OrderResponseDTO;
import com.ecommerce.backend.orders.infrastructure.mappers.OrderRequestMapper;
import com.ecommerce.backend.orders.infrastructure.mappers.OrderResponseMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

   
    private final OrderService orderService;
    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;

    public OrderController(OrderService orderService, OrderRequestMapper orderRequestMapper, OrderResponseMapper orderResponseMapper) {
        this.orderService = orderService;
        this.orderRequestMapper = orderRequestMapper;
        this.orderResponseMapper = orderResponseMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderDTO dto) {
        
        Order newOrder = orderRequestMapper.toDomain(dto);

        Order createdOrder = orderService.createOrder(newOrder);

        OrderResponseDTO response = orderResponseMapper.toResponseDTO(createdOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
