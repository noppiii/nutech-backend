package com.nutech.backend.service.transaction.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.*;
import com.nutech.backend.exception.CustomException;
import com.nutech.backend.payload.request.transaction.TopUpRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.transaction.TopUpResponse;
import com.nutech.backend.repository.TransactionRepository;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.repository.WalletRepository;
import com.nutech.backend.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

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
}
