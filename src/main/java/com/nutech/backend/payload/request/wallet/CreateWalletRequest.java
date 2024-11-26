package com.nutech.backend.payload.request.wallet;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateWalletRequest {

    @NotNull(message = "Saldo tidak boleh null")
    @Positive(message = "Saldo harus lebih besar dari nol")
    private BigDecimal balance;
}
