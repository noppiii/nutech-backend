package com.nutech.backend.payload.response;

import com.nutech.backend.entity.Product;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class GetAllProductResponse {

    private List<ProductDto> products;
    private Pagination pagination;

    public static GetAllProductResponse of(Page<Product> productPage) {
        Page<ProductDto> productDtoPage = productPage.map(ProductDto::of);
        return builder()
                .products(productDtoPage.getContent())
                .pagination(Pagination.of(productDtoPage))
                .build();
    }

    @Getter
    @Builder
    private static class ProductDto {
        private Long id;
        private String name;
        private BigDecimal price;
        private Long quantity;

        private static ProductDto of(Product product) {
            return ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .build();
        }
    }

    @Getter
    @Builder
    private static class Pagination {
        private int totalPages;
        private long totalElements;
        private int page;
        private boolean hasNext;
        private boolean hasPrevious;
        private int requestSize;
        private int productSize;

        private static Pagination of(Page<ProductDto> productDtoPage) {
            return builder()
                    .totalPages(productDtoPage.getTotalPages())
                    .totalElements(productDtoPage.getTotalElements())
                    .page(productDtoPage.getNumber() + 1)
                    .hasNext(productDtoPage.hasNext())
                    .hasPrevious(productDtoPage.hasPrevious())
                    .requestSize(productDtoPage.getSize())
                    .productSize(productDtoPage.getNumberOfElements())
                    .build();
        }
    }
}
