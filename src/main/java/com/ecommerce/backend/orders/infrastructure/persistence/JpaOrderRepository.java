package com.ecommerce.backend.orders.infrastructure.persistence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.backend.orders.domain.models.OrderStatus;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID>{

    List<OrderEntity> findByStatus(OrderStatus status);

    @Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.items WHERE o.id = :id")
    Optional<OrderEntity> findByIdWithItems(@Param("id") UUID id);
}
