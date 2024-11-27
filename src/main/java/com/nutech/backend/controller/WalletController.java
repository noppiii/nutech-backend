package com.nutech.backend.controller;

import com.nutech.backend.payload.request.transaction.TopUpRequest;
import com.nutech.backend.payload.request.wallet.CreateWalletRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.wallet.MyWalletResponse;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wallet", description = "Wallet API")
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

    @Operation(summary = "My Wallet", description = "Lihat informasi dompet saya (Lihat saldo dan transaksi terbaru).",
            parameters = {
                    @Parameter(
                            name = "accessToken",
                            description = "Header untuk access token (format: Bearer <accessToken>)",
                            required = true,
                            in = ParameterIn.HEADER)})
    @GetMapping
    public ResponseEntity<CustomSuccessResponse<MyWalletResponse>> myWallet() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<MyWalletResponse> response = walletService.myWallet(email);
        return ResponseEntity.ok(response);
    }
}
