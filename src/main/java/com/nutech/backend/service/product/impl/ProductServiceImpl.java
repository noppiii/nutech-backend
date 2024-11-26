package com.nutech.backend.service.product.impl;

import com.nutech.backend.constant.ErrorCode;
import com.nutech.backend.entity.Product;
import com.nutech.backend.entity.User;
import com.nutech.backend.exception.CustomException;
import com.nutech.backend.payload.request.product.ProductRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.GetAllProductResponse;
import com.nutech.backend.payload.response.product.ProductResponse;
import com.nutech.backend.repository.ProductRepository;
import com.nutech.backend.repository.UserRepository;
import com.nutech.backend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CustomSuccessResponse<ProductResponse> createProduct(ProductRequest request, String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        productRepository.save(product);

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();

        return new CustomSuccessResponse<>("200", "Berhasil membuat produk", response);
    }

    @Override
    public CustomSuccessResponse<ProductResponse> editProduct(Long id, ProductRequest request, String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        Product updatedProduct = product.toBuilder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        productRepository.save(updatedProduct);

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();

        return new CustomSuccessResponse<>("200", "Berhasil mengedit produk", response);
    }

    @Override
    public CustomSuccessResponse<GetAllProductResponse> getAllProducts(Pageable pageable) {

        Page<Product> products = productRepository.findAll(pageable);

        GetAllProductResponse getProductsResponse = GetAllProductResponse.of(products);
        return new CustomSuccessResponse<>("200", "Berhasil mendapatkan semua data produk", getProductsResponse);
    }

    @Override
    public CustomSuccessResponse<ProductResponse> getProductDetail(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductResponse detailProductResponse = ProductResponse.fromProduct(product);

        return new CustomSuccessResponse<>("200", "Berhasil mendapatkan data produk", detailProductResponse);
    }

    @Override
    public CustomSuccessResponse<String> deleteProduct(Long id, String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        return new CustomSuccessResponse<>("200", "Berhasil menghapus produk", "Produk berhasil dihapus.");
    }
}