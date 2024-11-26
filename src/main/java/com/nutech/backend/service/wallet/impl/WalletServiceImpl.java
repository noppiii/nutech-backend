package com.nutech.backend.service.wallet.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.User;
import com.nutech.backend.entity.Wallet;
import com.nutech.backend.exception.CustomException;
import com.nutech.backend.payload.request.wallet.CreateWalletRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.wallet.WalletResponse;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.repository.WalletRepository;
import com.nutech.backend.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    public CustomSuccessResponse<WalletResponse> createWallet(CreateWalletRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        if (walletRepository.existsByUser(user)) {
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
}