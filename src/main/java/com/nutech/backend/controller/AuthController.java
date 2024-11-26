package com.nutech.backend.controller;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.exception.BadRequestException;
import com.nutech.backend.payload.request.auth.LoginRequest;
import com.nutech.backend.payload.request.auth.RegisterRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.auth.LoginResponse;
import com.nutech.backend.security.JwtToken;
import com.nutech.backend.service.auth.AuthService;
import com.nutech.backend.util.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register User", description = "Register dengan memasukkan alamat email, password, dan nama Anda.")
    @PostMapping(value = "/register")
    public ResponseEntity<CustomSuccessResponse<String>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        CustomSuccessResponse<String> response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login", description = "Masukkan email dan kata sandi untuk login.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "Refresh Token", description = "Buat ulang refreshToken yang sudah kadaluwarsa.", parameters = {
            @Parameter(
                    name = "refreshToken",
                    description = "Header untuk refresh token (format: Bearer <refreshToken>)",
                    required = true,
                    in = ParameterIn.HEADER)})
    @GetMapping("/refresh")
    public JwtToken refreshToken(HttpServletRequest request) {
        String refreshToken = JwtTokenUtils.extractBearerToken(request.getHeader("refreshToken"));

        if (refreshToken.isBlank()) {
            throw new BadRequestException(ErrorCode.EMPTY_REFRESH_TOKEN);
        }
        return authService.refreshToken(refreshToken);
    }

    @Operation(summary = "Logout", description = "Logout akun saat ini.",
            parameters = {
                    @Parameter(
                            name = "accessToken",
                            description = "Header untuk token akses pengguna (format: Bearer <accessToken>)",
                            required = true,
                            in = ParameterIn.HEADER
                    )})
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<CustomSuccessResponse<String>> logoutUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(authService.logout(username));
    }
}
