package com.nutech.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNAUTHORIZED("Anda tidak diizinkan masuk. Pastikan Anda memiliki hak akses yang sesuai.", HttpStatus.UNAUTHORIZED),
    INVALID_TYPE_TOKEN("Jenis token yang diterima harus Bearer. Pastikan format token Anda benar.", HttpStatus.UNAUTHORIZED),
    EMPTY_AUTHORITY("Informasi otorisasi diperlukan untuk melanjutkan.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_ACCESS_TOKEN("Access token Anda telah kedaluwarsa. Silakan login ulang untuk mendapatkan token yang baru.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("Access token tidak valid. Pastikan token yang digunakan benar.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_REFRESH_TOKEN("Refresh token Anda telah kedaluwarsa. Silakan lakukan login untuk memperbarui token.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("Refresh token tidak valid. Pastikan token yang Anda gunakan benar.", HttpStatus.UNAUTHORIZED),
    LOGOUTED_TOKEN("Token sudah diproses untuk logout. Anda perlu login ulang untuk melanjutkan.", HttpStatus.OK),
    ALREADY_REGISTER("User dengan email ini sudah terdaftar. Gunakan email yang berbeda.", HttpStatus.CONFLICT),
    EMAIL_NOT_FOUND("Email tidak ditemukan. Pastikan Anda memasukkan email yang valid.", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("Email atau password yang Anda masukkan tidak sesuai. Coba lagi.", HttpStatus.UNAUTHORIZED),
    EMPTY_REFRESH_TOKEN("Refresh token diperlukan untuk memperbarui akses token.", HttpStatus.BAD_REQUEST),
    SNATCH_TOKEN("Refresh token tidak valid atau telah digunakan oleh pihak lain. Silakan login ulang untuk keamanan akun Anda.", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED("Anda belum terautentikasi. Silakan login terlebih dahulu.", HttpStatus.UNAUTHORIZED),
    WALLET_ALREADY_EXISTS("Wallet untuk user ini sudah ada. Anda hanya dapat memiliki satu wallet.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("User tidak ditemukan dengan email tersebut. Pastikan email yang Anda masukkan benar.", HttpStatus.NOT_FOUND),
    WALLET_NOT_FOUND("Wallet tidak ditemukan untuk user ini. Pastikan wallet telah dibuat terlebih dahulu.", HttpStatus.NOT_FOUND),
    INVALID_PAYMENT_METHOD("Payment method harus salah satu dari DANA, SHOPEEPAY, BNI, BRI, MANDIRI, E_WALLET, BANK_TRANSFER, CREDIT_CARD", HttpStatus.CONFLICT),
    PRODUCT_NOT_FOUND("Data product tidak ditemukan", HttpStatus.NOT_FOUND),
    INSUFFICIENT_PRODUCT_QUANTITY("Product yang anda beli stocknya habis atau melebihi stock yang ada", HttpStatus.CONFLICT),
    INSUFFICIENT_WALLET_BALANCE("Saldo tidak mencukupi untuk pembelian", HttpStatus.CONFLICT),
    INVALID_PRODUCT_QUANTITY("Stock product tidak sesuai format", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
