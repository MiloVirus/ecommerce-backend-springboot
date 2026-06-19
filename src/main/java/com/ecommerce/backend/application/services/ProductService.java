package com.ecommerce.backend.application.services;
import java.util.List;
import java.util.Optional;

import org.apache.kafka.common.errors.ResourceNotFoundException;
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

    public void deleteProduct(Long id)
    {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " does not exist"));

        Product deactivatedProduct = new Product(
            product.id(),
            product.name(),
            product.description(),
            product.price(),
            product.stock(),
            false
        );

        productRepository.save(deactivatedProduct);
    }
}
