package com.nutech.backend.service.wallet.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.Transaction;
import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import com.nutech.backend.exception.CustomException;
import com.nutech.backend.payload.request.wallet.CreateWalletRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.transaction.TransactionResponse;
import com.nutech.backend.payload.response.wallet.MyWalletResponse;
import com.nutech.backend.payload.response.wallet.WalletResponse;
import com.nutech.backend.repository.TransactionRepository;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.repository.WalletRepository;
import com.nutech.backend.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public CustomSuccessResponse<WalletResponse> createWallet(CreateWalletRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        if (walletRepository.existsByUser(user.getId())) {
            throw new CustomException(ErrorCode.WALLET_ALREADY_EXISTS);
        }

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(request.getBalance())
                .build();

        walletRepository.save(wallet);
        WalletResponse walletResponse = WalletResponse.fromWallet(wallet);

        return new CustomSuccessResponse<>("200", "Berhasil membuat dompet", walletResponse);
    }

    public CustomSuccessResponse<MyWalletResponse> myWallet(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));

        Wallet wallet = walletRepository.findByUser(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));
        List<Transaction> transactions = transactionRepository.findByUser(user.getId());
        List<TransactionResponse> transactionResponses = transactions.stream()
                .map(TransactionResponse::fromTransaction)
                .collect(Collectors.toList());

        MyWalletResponse myWalletResponse = new MyWalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                user.getName(),
                user.getEmail(),
                transactionResponses
        );

        return new CustomSuccessResponse<>("200", "Berhasil mendapatkan informasi dompet", myWalletResponse);
    }
}
