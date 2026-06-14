package com.ecommerce.backend.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.ecommerce.backend.domain.models.Product;


public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();    
}
