package com.nutech.backend.entity;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.audit.BaseTimeEntity;
import com.nutech.backend.exception.CustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE products SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "products")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Builder
    public Product(String name, BigDecimal price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void editProduct(String name, BigDecimal price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setQuantity(Long newQuantity) {
        if (newQuantity < 0) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_QUANTITY);
        }
        this.quantity = newQuantity;
    }
}