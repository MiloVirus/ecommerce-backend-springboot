package com.ecommerce.backend.products.infrastructure.adapters;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component; // 🔌 ¡No olvides la anotación de Spring aquí!

import com.ecommerce.backend.products.application.services.ProductService;
import com.ecommerce.backend.products.domain.models.Product;
import com.ecommerce.backend.products.domain.models.ports.outbound.ProductQueriesPort;

@Component // 🚀 Asegúrate de registrarlo como componente
public class ProductQueriesAdapter implements ProductQueriesPort {

    private final ProductService productService;

    public ProductQueriesAdapter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Map<Long, BigDecimal> getPricesForProducts(List<Long> productIds) {
        
        return productIds.stream()
                .distinct() 
                .map(productId -> productService.getProductById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + productId)))
                .collect(Collectors.toMap(
                    Product::id,    
                    Product::price  
                ));
    }
}
