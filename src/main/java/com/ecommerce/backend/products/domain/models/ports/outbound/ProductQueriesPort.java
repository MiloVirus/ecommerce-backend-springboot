package com.ecommerce.backend.products.domain.models.ports.outbound;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductQueriesPort {
    Map<Long, BigDecimal> getPricesForProducts(List<Long> productIds);
}
