package com.nutech.backend.payload.response.product;

import com.nutech.backend.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponse {

    private final Long id;
    private final String name;
    private final Long quantity;
    private final BigDecimal price;

    @Builder
    public ProductResponse(Long id, String name, Long quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
