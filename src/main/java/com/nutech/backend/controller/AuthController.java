package com.nutech.backend.controller;

import com.nutech.backend.payload.request.auth.RegisterRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "users", description = "Users API")
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
}
