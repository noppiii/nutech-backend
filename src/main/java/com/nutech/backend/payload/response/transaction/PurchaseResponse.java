package com.nutech.backend.payload.response.transaction;

import com.nutech.backend.entity.Transaction;
import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseResponse {

    private TransactionResponse transaction;
    private List<PurchasedItemResponse> purchasedItems;

    public PurchaseResponse(TransactionResponse transaction, List<PurchasedItemResponse> purchasedItems) {
        this.transaction = transaction;
        this.purchasedItems = purchasedItems;
    }

    public static PurchaseResponse fromTransaction(Transaction transaction, List<PurchasedItemResponse> purchasedItems) {
        return new PurchaseResponse(
                TransactionResponse.fromTransaction(transaction),
                purchasedItems
        );
    }
}