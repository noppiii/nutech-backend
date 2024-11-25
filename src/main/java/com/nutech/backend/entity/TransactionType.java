package com.nutech.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    TOP_UP("Top Up"),
    PAYMENT("Payment");

    private final String description;
}
