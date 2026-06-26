package com.ecommerce.backend.orders.application.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderStatus;
import com.ecommerce.backend.orders.domain.repositories.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order)
    {
        return orderRepository.saveOrder(order);
    }

    public Optional<Order> findOrderById(UUID id)
    {
        return orderRepository.findById(id);
    }

    public List<Order> findByStatus(OrderStatus orderStatus)
    {
        return orderRepository.findByStatus(orderStatus);
    }
 }
