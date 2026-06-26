package com.ecommerce.backend.orders.infrastructure.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderItem;
import com.ecommerce.backend.orders.domain.models.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") 
    private List<OrderItemEntity> items = new ArrayList<>();

    public OrderEntity() {}

    public static OrderEntity fromDomain(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.id()); 
        entity.setCustomerId(order.customerId());
        entity.setStatus(order.status());
        entity.setTotalAmount(order.totalAmount());
        
        List<OrderItemEntity> itemEntities = order.items().stream()
                .map(item -> new OrderItemEntity(
                    item.productId(), 
                    item.quantity(), 
                    item.priceAtPurchase()
                ))
                .collect(Collectors.toCollection(ArrayList::new));
        entity.setItems(itemEntities);
        
        return entity;
    }

    public Order toDomain() {
        List<OrderItem> domainItems = this.items.stream()
                .map(itemEntity -> new OrderItem(
                        itemEntity.getId(), 
                        itemEntity.getProductId(),
                        itemEntity.getQuantity(),
                        itemEntity.getPriceAtPurchase()
                )).toList();

        return new Order(
                this.id,
                this.customerId,
                this.status,
                domainItems,
                this.totalAmount
        );
    }


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<OrderItemEntity> getItems() { return items; }
    public void setItems(List<OrderItemEntity> items) { this.items = items; }
}