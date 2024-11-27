package com.nutech.backend.payload.request.transaction;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Getter
@Setter
public class PurchaseRequest {

    @NotNull(message = "Payment method harus diisi")
    @Pattern(regexp = "^(E_WALLET|BANK_TRANSFER|CREDIT_CARD|DANA|SHOPEEPAY|BNI|BRI|MANDIRI)$",
            message = "Payment method harus salah satu dari E_WALLET, BANK_TRANSFER, CREDIT_CARD, DANA, SHOPEEPAY, BNI, BRI, MANDIRI")
    private String paymentMethod;

    @NotEmpty(message = "Daftar produk tidak boleh kosong.")
    @Valid
    private List<PurchaseItem> items;

    @Getter
    @Setter
    public static class PurchaseItem {
        @NotNull(message = "Product ID tidak boleh kosong.")
        private Long productId;

        @NotNull(message = "Jumlah pembelian tidak boleh kosong.")
        @Min(value = 1, message = "Jumlah pembelian minimal adalah 1.")
        private Integer quantity;
    }
}
