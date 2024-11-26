package com.nutech.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    TOP_UP("Top Up"),
    PURCHASE("Purchase");

    private final String name;

    public static TransactionType fromString(String transactionType) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name.replace(" ", "").equalsIgnoreCase(transactionType.replace(" ", "").replace("_", ""))) {
                return type;
            }
        }
        throw new IllegalArgumentException("Jenis transaksi tidak dikenal: " + transactionType);
    }
}


