package com.nutech.backend.entity;

import com.nutech.backend.entity.audit.BaseTimeEntity;
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
@SQLDelete(sql = "UPDATE purchases SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "purchases")
public class Purchase extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Builder
    public Purchase(Transaction transaction, Product product, Integer quantity) {
        this.transaction = transaction;
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal calculateTotalPrice() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        if (!transaction.getPurchases().contains(this)) {
            transaction.getPurchases().add(this);
        }
    }
}
