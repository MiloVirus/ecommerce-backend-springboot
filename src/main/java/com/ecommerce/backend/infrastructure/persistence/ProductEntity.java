package com.ecommerce.backend.infrastructure.persistence;
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

    public ProductEntity() {}

    public ProductEntity(Long id, String name, String description, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
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

    public com.ecommerce.backend.domain.models.Product toDomain() {
        return new com.ecommerce.backend.domain.models.Product(
            this.id,
            this.name,
            this.description,
            this.price,
            this.stock
        );
    }

    public static ProductEntity fromDomain(com.ecommerce.backend.domain.models.Product product) {
        return new ProductEntity(
            product.id(),
            product.name(),
            product.description(),
            product.price(),
            product.stock()
        );
    }
}
