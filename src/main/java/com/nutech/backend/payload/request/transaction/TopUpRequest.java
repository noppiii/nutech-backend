package com.nutech.backend.payload.request.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TopUpRequest {

    @NotNull(message = "Jumlah harus diisi")
    @DecimalMin(value = "0.01", message = "Jumlah harus lebih besar dari nol")
    private BigDecimal amount;

    private String transactionName;

    private String transactionDescription;

    @NotNull(message = "Payment method harus diisi")
    @Pattern(regexp = "^(E_WALLET|BANK_TRANSFER|CREDIT_CARD|DANA|SHOPEEPAY|BNI|BRI|MANDIRI)$",
            message = "Payment method harus salah satu dari E_WALLET, BANK_TRANSFER, CREDIT_CARD, DANA, SHOPEEPAY, BNI, BRI, MANDIRI")
    private String paymentMethod;
}
