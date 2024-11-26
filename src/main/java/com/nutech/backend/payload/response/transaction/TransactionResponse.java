package com.nutech.backend.payload.response.transaction;

import com.nutech.backend.entity.Transaction;
import com.nutech.backend.entity.PaymentMethod;
import com.nutech.backend.entity.TransactionType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class TransactionResponse {

    private Long transactionId;
    private String name;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private PaymentMethod paymentMethod;
    private LocalDateTime transactionDate;

    public TransactionResponse(Long transactionId, String name, String description, BigDecimal amount, TransactionType type, PaymentMethod paymentMethod, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
    }

    public static TransactionResponse fromTransaction(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getName(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getPaymentMethod(),
                transaction.getCreatedAt()
        );
    }
}

