package com.nutech.backend.payload.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email wajib diisi.")
    @Email(message = "Masukkan email sesuai format yang benar.")
    private String email;

    @NotBlank(message = "Kata sandi wajib diisi.")
    @Size(min = 8, max = 20, message = "Kata sandi harus terdiri dari minimal 8 dan maksimal 20 karakter.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$", message = "Kata sandi harus mengandung minimal satu huruf besar, satu huruf kecil, satu angka, dan satu karakter khusus.")
    private String password;

    @NotBlank(message = "Nama panggilan wajib diisi.")
    @Size(min = 2, max = 20, message = "Nama harus terdiri dari minimal 2 dan maksimal 20 karakter.")
    private String name;
}
