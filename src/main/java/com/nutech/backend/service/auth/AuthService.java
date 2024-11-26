package com.nutech.backend.service.auth;

import com.nutech.backend.payload.request.auth.LoginRequest;
import com.nutech.backend.payload.request.auth.RegisterRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.auth.LoginResponse;
import com.nutech.backend.security.JwtToken;

public interface AuthService {

    CustomSuccessResponse<String> register(RegisterRequest registerRequest) ;
    LoginResponse login(LoginRequest loginRequest);
    JwtToken refreshToken(String requestRefreshToken);
    CustomSuccessResponse<String> logout(String email);
}
