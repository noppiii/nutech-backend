package com.nutech.backend.payload.response.transaction;

import com.nutech.backend.entity.Purchase;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PurchasedItemResponse {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public PurchasedItemResponse(Long productId, String productName, Integer quantity, BigDecimal price, BigDecimal totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public static PurchasedItemResponse fromPurchase(Purchase purchase) {
        return new PurchasedItemResponse(
                purchase.getProduct().getId(),
                purchase.getProduct().getName(),
                purchase.getQuantity(),
                purchase.getProduct().getPrice(),
                purchase.calculateTotalPrice()
        );
    }
}

