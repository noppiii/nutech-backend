package com.nutech.backend.controller;

import com.nutech.backend.payload.request.wallet.CreateWalletRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.wallet.WalletResponse;
import com.nutech.backend.service.wallet.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "users", description = "Auth API")
@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "Create Wallet", description = "Buat dompet baru untuk transaksi.", parameters = {
            @Parameter(
                    name = "accessToken",
                    description = "Header untuk access token (format: Bearer <accessToken>)",
                    required = true,
                    in = ParameterIn.HEADER)})
    @PostMapping
    public ResponseEntity<CustomSuccessResponse<WalletResponse>> createWallet(@RequestBody @Valid CreateWalletRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<WalletResponse> response = walletService.createWallet(request, email);
        return ResponseEntity.ok(response);
    }
}
