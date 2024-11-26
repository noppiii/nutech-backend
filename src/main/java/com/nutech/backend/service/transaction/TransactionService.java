package com.nutech.backend.service.transaction;

import com.nutech.backend.payload.request.transaction.TopUpRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.transaction.TopUpResponse;

public interface TransactionService {

    CustomSuccessResponse<TopUpResponse> topUp(TopUpRequest request, String email);
}
