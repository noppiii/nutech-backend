package com.nutech.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNAUTHORIZED("Anda tidak diizinkan masuk.", HttpStatus.UNAUTHORIZED),
    INVALID_TYPE_TOKEN("Jenis token harus Bearer.", HttpStatus.UNAUTHORIZED),
    EMPTY_AUTHORITY("Informasi otorisasi diperlukan.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_ACCESS_TOKEN("AccessToken telah kedaluwarsa.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("AccessToken tidak valid.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_REFRESH_TOKEN("RefreshToken telah kedaluwarsa.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("RefreshToken tidak valid.", HttpStatus.UNAUTHORIZED),
    LOGOUTED_TOKEN("Token sudah diproses untuk logout.", HttpStatus.OK),
    ALREADY_REGISTER("User sudah ada.", HttpStatus.CONFLICT),
    EMAIL_NOT_FOUND("Email user tidak ditemukan.", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("Email atau password tidak sesuai", HttpStatus.UNAUTHORIZED),
    EMPTY_REFRESH_TOKEN("RefreshToken diperlukan.", HttpStatus.BAD_REQUEST),
    SNATCH_TOKEN("Refresh token tidak valid atau telah digunakan oleh pihak lain. Silakan login ulang untuk keamanan akun Anda.", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED("Unauthenticated.", HttpStatus.UNAUTHORIZED),
    WALLET_ALREADY_EXISTS("Wallet already exists.", HttpStatus.CONFLICT),;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
