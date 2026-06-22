package com.ecommerce.backend.products.infrastructure.persistence;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")

public class ProductEntity {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean isActive = true;

    public ProductEntity() {}

    public ProductEntity(Long id, String name, String description, BigDecimal price, Integer stock, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public com.ecommerce.backend.products.domain.models.Product toDomain() {
        return new com.ecommerce.backend.products.domain.models.Product(
            this.id,
            this.name,
            this.description,
            this.price,
            this.stock,
            this.isActive
        );
    }

    public static ProductEntity fromDomain(com.ecommerce.backend.products.domain.models.Product product) {
        return new ProductEntity(
            product.id(),
            product.name(),
            product.description(),
            product.price(),
            product.stock(),
            product.isActive()
        );
    }
}
