package com.nutech.backend.controller;

import com.nutech.backend.payload.request.product.ProductRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.GetAllProductResponse;
import com.nutech.backend.payload.response.product.ProductResponse;
import com.nutech.backend.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create Product", description = "Buat product baru.", parameters = {
            @Parameter(
                    name = "accessToken",
                    description = "Header untuk access token (format: Bearer <accessToken>)",
                    required = true,
                    in = ParameterIn.HEADER)})
    @PostMapping
    public ResponseEntity<CustomSuccessResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<ProductResponse> response = productService.createProduct(request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Edit Product", description = "Edit product yang sudah ada.", parameters = {
            @Parameter(
                    name = "accessToken",
                    description = "Header untuk access token (format: Bearer <accessToken>)",
                    required = true,
                    in = ParameterIn.HEADER)})
    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<ProductResponse>> editProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<ProductResponse> response = productService.editProduct(id, request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get All Products", description = "Dapatkan semua produk dengan pagination.")
    @GetMapping
    public ResponseEntity<CustomSuccessResponse<GetAllProductResponse>> getAllProducts(@PageableDefault(size = 8, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        CustomSuccessResponse<GetAllProductResponse> response = productService.getAllProducts(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Product Detail", description = "Dapatkan detail produk berdasarkan ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<ProductResponse>> getProductDetail(@PathVariable Long id) {
        CustomSuccessResponse<ProductResponse> response = productService.getProductDetail(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete Product", description = "Hapus produk berdasarkan ID.", parameters = {
            @Parameter(
                    name = "accessToken",
                    description = "Header untuk access token (format: Bearer <accessToken>)",
                    required = true,
                    in = ParameterIn.HEADER)})
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<String>> deleteProduct(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<String> response = productService.deleteProduct(id, email);
        return ResponseEntity.ok(response);
    }
}

