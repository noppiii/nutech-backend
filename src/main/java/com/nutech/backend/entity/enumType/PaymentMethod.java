package com.nutech.backend.entity.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    E_WALLET("E-Wallet"),
    BANK_TRANSFER("Bank Transfer"),
    CREDIT_CARD("Credit Card"),
    DANA("DANA"),
    SHOPEEPAY("ShopeePay"),
    BNI("BNI"),
    BRI("BRI"),
    MANDIRI("Mandiri");

    private final String paymentName;

    public static PaymentMethod fromString(String paymentMethod) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.name().equalsIgnoreCase(paymentMethod)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Metode pembayaran tidak dikenal: " + paymentMethod);
    }
}


