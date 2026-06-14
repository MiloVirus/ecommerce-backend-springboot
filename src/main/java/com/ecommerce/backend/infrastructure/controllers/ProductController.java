package com.ecommerce.backend.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.application.services.ProductService;
import com.ecommerce.backend.domain.models.Product;
import com.ecommerce.backend.infrastructure.dtos.CreateProductDTO;
import com.ecommerce.backend.infrastructure.dtos.ProductResponseDTO;

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
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody CreateProductDTO dto)
    {
        Product productToCreate = new Product(null, dto.name(), dto.description(), dto.price(), dto.stock());

        Product createdProduct = productService.createProduct(productToCreate);

        ProductResponseDTO response = new ProductResponseDTO(
            createdProduct.id(),
            createdProduct.name(),
            createdProduct.description(),
            createdProduct.price()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> productsRecieved = productService.getAllProducts();
        return ResponseEntity.ok(productsRecieved);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}
