package com.ecommerce.backend.orders.infrastructure.mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ecommerce.backend.orders.domain.models.Order;
import com.ecommerce.backend.orders.domain.models.OrderItem;
import com.ecommerce.backend.orders.domain.models.OrderStatus;
import com.ecommerce.backend.orders.infrastructure.dtos.CreateOrderDTO;
import com.ecommerce.backend.products.domain.models.ports.outbound.ProductQueriesPort; 

@Component
public class OrderRequestMapper {

    private final ProductQueriesPort productQueriesPort;


    public OrderRequestMapper(ProductQueriesPort productQueriesPort) {
        this.productQueriesPort = productQueriesPort;
    }

    public Order toDomain(CreateOrderDTO dto) {
       
        List<Long> productIds = dto.orderItems().stream()
                .map(item -> item.productId())
                .toList();

      
        Map<Long, BigDecimal> priceMap = productQueriesPort.getPricesForProducts(productIds);

      
        List<OrderItem> domainItems = dto.orderItems().stream()
                .map(itemDto -> {
                    BigDecimal realPrice = priceMap.get(itemDto.productId());
                    return new OrderItem(
                        null, 
                        itemDto.productId(), 
                        itemDto.quantity(), 
                        realPrice
                    );
                })
                .toList();

        
        BigDecimal totalAmount = domainItems.stream()
                .map(item -> item.priceAtPurchase().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

      
        return new Order(null, dto.customerId(), OrderStatus.PENDING, domainItems, totalAmount);
    }
}