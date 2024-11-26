package com.nutech.backend.service.auth;

import com.nutech.backend.payload.request.auth.RegisterRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;

public interface AuthService {

    CustomSuccessResponse<String> register(RegisterRequest registerRequest) ;
}
