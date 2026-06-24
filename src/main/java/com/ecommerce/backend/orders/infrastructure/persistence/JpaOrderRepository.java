package com.ecommerce.backend.orders.infrastructure.persistence;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.orders.domain.models.OrderStatus;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID>{

    List<OrderEntity> findByStatus(OrderStatus status);
}
