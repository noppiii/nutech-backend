package com.nutech.backend.payload.response.wallet;

import com.nutech.backend.entity.Wallet;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WalletResponse {

    private final Long id;
    private final Long userId;
    private final String name;
    private final BigDecimal balance;

    @Builder
    public WalletResponse(Long id, Long userId, String name, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public static WalletResponse fromWallet(Wallet wallet) {
        return WalletResponse.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .name(wallet.getUser().getName())
                .balance(wallet.getBalance())
                .build();
    }
}
