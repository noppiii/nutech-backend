package com.nutech.backend.service.wallet;

import com.nutech.backend.payload.request.wallet.CreateWalletRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.wallet.MyWalletResponse;
import com.nutech.backend.payload.response.wallet.WalletResponse;

public interface WalletService {

    CustomSuccessResponse<WalletResponse> createWallet(CreateWalletRequest request, String email);
    CustomSuccessResponse<MyWalletResponse> myWallet(String email);
}
