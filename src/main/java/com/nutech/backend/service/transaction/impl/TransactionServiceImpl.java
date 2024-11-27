package com.nutech.backend.service.transaction.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.*;
import com.nutech.backend.exception.CustomException;
import com.nutech.backend.payload.request.transaction.PurchaseRequest;
import com.nutech.backend.payload.request.transaction.TopUpRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.transaction.PurchaseResponse;
import com.nutech.backend.payload.response.transaction.PurchasedItemResponse;
import com.nutech.backend.payload.response.transaction.TopUpResponse;
import com.nutech.backend.repository.ProductRepository;
import com.nutech.backend.repository.TransactionRepository;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.repository.WalletRepository;
import com.nutech.backend.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Override
    public CustomSuccessResponse<TopUpResponse> topUp(TopUpRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        PaymentMethod paymentMethod = PaymentMethod.fromString(request.getPaymentMethod());

        Transaction transaction = Transaction.builder()
                .user(user)
                .name(request.getTransactionName() != null ? request.getTransactionDescription() : "Top-Up: " + paymentMethod.getPaymentName())
                .description(request.getTransactionDescription())
                .amount(request.getAmount())
                .type(TransactionType.fromString("TOP_UP"))
                .paymentMethod(paymentMethod)
                .build();

        transactionRepository.save(transaction);
        wallet.addBalance(request.getAmount());
        walletRepository.save(wallet);

        TopUpResponse topUpResponse = TopUpResponse.fromWalletAndTransaction(wallet, transaction);

        return new CustomSuccessResponse<>("200", "Top-up berhasil dilakukan", topUpResponse);
    }

    @Override
    @Transactional
    public CustomSuccessResponse<PurchaseResponse> purchase(PurchaseRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));
        List<PurchaseRequest.PurchaseItem> items = request.getItems();
        List<Purchase> purchases = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseRequest.PurchaseItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

            if (product.getQuantity() < item.getQuantity()) {
                throw new CustomException(ErrorCode.INSUFFICIENT_PRODUCT_QUANTITY);
            }

            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(totalPrice);

            Purchase purchase = Purchase.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .build();
            purchases.add(purchase);
        }

        if (wallet.getBalance().compareTo(totalAmount) < 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_WALLET_BALANCE);
        }

        wallet.subtractBalance(totalAmount);
        walletRepository.save(wallet);

        for (Purchase purchase : purchases) {
            Product product = purchase.getProduct();
            product.setQuantity(product.getQuantity() - purchase.getQuantity());
            productRepository.save(product);
        }

        PaymentMethod paymentMethod = PaymentMethod.fromString(request.getPaymentMethod());

        Transaction transaction = Transaction.builder()
                .user(user)
                .name("Pembelian Produk")
                .description("Pembelian berbagai produk.")
                .amount(totalAmount)
                .type(TransactionType.PURCHASE)
                .paymentMethod(paymentMethod)
                .build();

        for (Purchase purchase : purchases) {
            purchase.setTransaction(transaction);
        }

        transactionRepository.save(transaction);

        List<PurchasedItemResponse> purchasedItems = purchases.stream()
                .map(PurchasedItemResponse::fromPurchase)
                .toList();
        PurchaseResponse purchaseResponse = PurchaseResponse.fromTransaction(transaction, purchasedItems);

        return new CustomSuccessResponse<>("200", "Pembelian berhasil dilakukan", purchaseResponse);
    }
}
