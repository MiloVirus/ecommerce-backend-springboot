package com.ecommerce.backend.products.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.products.application.services.ProductService;
import com.ecommerce.backend.products.domain.models.Product;
import com.ecommerce.backend.products.infrastructure.dtos.CreateProductDTO;
import com.ecommerce.backend.products.infrastructure.dtos.ProductCreatedResponseDTO;
import com.ecommerce.backend.products.infrastructure.dtos.ProductResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductCreatedResponseDTO> createProduct(@Valid @RequestBody CreateProductDTO dto)
    {
        Product productToCreate = new Product(null, dto.name(), dto.description(), dto.price(), dto.stock(), true);

        Product createdProduct = productService.createProduct(productToCreate);

        ProductCreatedResponseDTO response = new ProductCreatedResponseDTO(
            createdProduct.id(),
            createdProduct.name(),
            createdProduct.description(),
            createdProduct.price()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
        public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
            
            List<ProductResponseDTO> convertedList = productService.getAllProducts()
                    .stream()
                    .map(p -> new ProductResponseDTO(p.id(), p.name(), p.description(), p.price()))
                    .toList(); 

            return ResponseEntity.ok(convertedList);
}

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product)) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)
        {
            productService.deleteProduct(id);

            return ResponseEntity.noContent().build();
        }
    
    
}
