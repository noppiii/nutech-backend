package com.nutech.backend.service.product;

import com.nutech.backend.payload.request.product.ProductRequest;
import com.nutech.backend.payload.response.CustomSuccessResponse;
import com.nutech.backend.payload.response.GetAllProductResponse;
import com.nutech.backend.payload.response.product.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    CustomSuccessResponse<ProductResponse> createProduct(ProductRequest request, String email);

    CustomSuccessResponse<ProductResponse> editProduct(Long id, ProductRequest request, String email);

    CustomSuccessResponse<GetAllProductResponse> getAllProducts(Pageable pageable);

    CustomSuccessResponse<ProductResponse> getProductDetail(Long id);

    CustomSuccessResponse<String> deleteProduct(Long id, String email);
}
