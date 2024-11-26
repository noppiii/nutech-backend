package com.nutech.backend.payload.response.transaction;

import com.nutech.backend.entity.Transaction;
import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TopUpResponse {

    private BigDecimal yourBalance;
    private String userName;
    private String userEmail;
    private BigDecimal topUpAmount;
    private TransactionResponse transactions;

    public TopUpResponse(BigDecimal yourBalance, String userName, String userEmail, BigDecimal topUpAmount, TransactionResponse transactions) {
        this.yourBalance = yourBalance;
        this.userName = userName;
        this.userEmail = userEmail;
        this.topUpAmount = topUpAmount;
        this.transactions = transactions;
    }

    public static TopUpResponse fromWalletAndTransaction(Wallet wallet, Transaction transaction) {
        User user = wallet.getUser();

        TransactionResponse transactionResponses = TransactionResponse.fromTransaction(transaction);

        return new TopUpResponse(
                wallet.getBalance(),
                user.getName(),
                user.getEmail(),
                transaction.getAmount(),
                transactionResponses
        );
    }
}
