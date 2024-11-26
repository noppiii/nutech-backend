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
    ALREADY_REGISTER("User sudah ada.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
