package com.nutech.backend.payload.response.wallet;

import com.nutech.backend.entity.Transaction;
import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import com.nutech.backend.payload.response.transaction.TransactionResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyWalletResponse {

    private Long walletId;
    private BigDecimal balance;
    private String userName;
    private String userEmail;
    private List<TransactionResponse> transactions;

    public MyWalletResponse(Long walletId, BigDecimal balance, String userName, String userEmail, List<TransactionResponse> transactions) {
        this.walletId = walletId;
        this.balance = balance;
        this.userName = userName;
        this.userEmail = userEmail;
        this.transactions = transactions;
    }

    public static MyWalletResponse fromWalletAndTransactions(Wallet wallet, List<Transaction> transactions) {
        User user = wallet.getUser();

        List<TransactionResponse> transactionResponses = transactions.stream()
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .limit(5)
                .map(TransactionResponse::fromTransaction)
                .collect(Collectors.toList());

        return new MyWalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                user.getName(),
                user.getEmail(),
                transactionResponses
        );
    }
}