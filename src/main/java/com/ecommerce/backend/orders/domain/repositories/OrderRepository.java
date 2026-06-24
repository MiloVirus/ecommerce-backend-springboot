package com.ecommerce.backend.orders.domain.repositories;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderStatus;

public interface OrderRepository {
    Order saveOrder(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAllOrders();

    List<Order> findByStatus(OrderStatus orderStatus);

}
