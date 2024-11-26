package com.nutech.backend.payload.request.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Nama produk tidak boleh kosong.")
    @Size(max = 255, message = "Nama produk maksimal 255 karakter.")
    private String name;

    @NotNull(message = "Harga produk tidak boleh kosong.")
    @DecimalMin(value = "0.1", inclusive = false, message = "Harga produk harus lebih besar dari 0.")
    private BigDecimal price;

    @NotNull(message = "Kuantitas produk tidak boleh kosong.")
    @Min(value = 1, message = "Kuantitas produk minimal 1.")
    private Long quantity;
}
