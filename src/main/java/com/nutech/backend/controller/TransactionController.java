package com.nutech.backend.controller;

import com.nutech.backend.payload.request.transaction.PurchaseRequest;
import com.nutech.backend.payload.request.transaction.TopUpRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.transaction.PurchaseResponse;
import com.nutech.backend.payload.response.transaction.TopUpResponse;
import com.nutech.backend.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transaction", description = "Transaction API")
@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Top Up", description = "Top up saldo dompet.",
            parameters = {
                    @Parameter(
                            name = "accessToken",
                            description = "Header untuk access token (format: Bearer <accessToken>)",
                            required = true,
                            in = ParameterIn.HEADER)})
    @PostMapping("/top-up")
    public ResponseEntity<CustomSuccessResponse<TopUpResponse>> topUp(@RequestBody @Valid TopUpRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<TopUpResponse> response = transactionService.topUp(request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Purchase", description = "Pembelian Barang.",
            parameters = {
                    @Parameter(
                            name = "accessToken",
                            description = "Header untuk access token (format: Bearer <accessToken>)",
                            required = true,
                            in = ParameterIn.HEADER)})
    @PostMapping("/purchase")
    public ResponseEntity<CustomSuccessResponse<PurchaseResponse>> purchase(@RequestBody @Valid PurchaseRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<PurchaseResponse> response = transactionService.purchase(request, email);
        return ResponseEntity.ok(response);
    }
}
