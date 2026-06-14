package com.ecommerce.backend.application.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.domain.models.Product;
import com.ecommerce.backend.domain.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product)
    {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }
}
