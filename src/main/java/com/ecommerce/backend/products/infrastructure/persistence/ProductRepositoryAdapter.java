package com.ecommerce.backend.products.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.products.domain.models.Product;
import com.ecommerce.backend.products.domain.repositories.ProductRepository;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductRepositoryAdapter(JpaProductRepository jpaProductRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.jpaProductRepository = jpaProductRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductEntity.fromDomain(product);
        ProductEntity savedEntity = jpaProductRepository.save(entity);
        Product savedProduct = savedEntity.toDomain();

        String messageEvent = "Producto Creado -> ID: " + savedProduct.id() + ", Nombre: " + savedProduct.name();

        kafkaTemplate.send("product-events", messageEvent);

        return savedProduct;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(ProductEntity::toDomain); 
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain) 
                .collect(Collectors.toList());
    }


    @Override
    public Product updateProduct(Product product)
    {
        ProductEntity entity = ProductEntity.fromDomain(product);
        ProductEntity updatedEntity = jpaProductRepository.save(entity);
        Product updatedProduct = updatedEntity.toDomain();
        
        return updatedProduct;
        
    }
}