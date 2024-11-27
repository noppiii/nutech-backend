package com.nutech.backend.service.transaction;

import com.nutech.backend.payload.request.transaction.PurchaseRequest;
import com.nutech.backend.payload.request.transaction.TopUpRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.transaction.HistoryTransactionResponse;
import com.nutech.backend.payload.response.transaction.PurchaseResponse;
import com.nutech.backend.payload.response.transaction.TopUpResponse;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    CustomSuccessResponse<TopUpResponse> topUp(TopUpRequest request, String email);
    CustomSuccessResponse<PurchaseResponse> purchase(PurchaseRequest request, String email);
    CustomSuccessResponse<HistoryTransactionResponse> history(Pageable pageable, String email);
}
