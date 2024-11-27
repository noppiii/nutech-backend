package com.nutech.backend.payload.response.transaction;

import com.nutech.backend.entity.Transaction;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class HistoryTransactionResponse {

    private List<TransactionDto> transactions;
    private Pagination pagination;

    public static HistoryTransactionResponse of(Page<Transaction> transactionPage) {
        Page<TransactionDto> transactionDtoPage = transactionPage.map(TransactionDto::of);
        return builder()
                .transactions(transactionDtoPage.getContent())
                .pagination(Pagination.of(transactionDtoPage))
                .build();
    }

    @Getter
    @Builder
    private static class TransactionDto {
        private Long id;
        private String name;
        private String description;
        private BigDecimal amount;
        private String paymentMethod;
        private String transactionType;
        private LocalDateTime createdAt;

        private static TransactionDto of(Transaction transaction) {
            return TransactionDto.builder()
                    .id(transaction.getId())
                    .name(transaction.getName())
                    .description(transaction.getDescription())
                    .amount(transaction.getAmount())
                    .paymentMethod(transaction.getPaymentMethod().toString())
                    .transactionType(transaction.getType().toString())
                    .createdAt(transaction.getCreatedAt())
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
        private int transactionSize;

        private static Pagination of(Page<TransactionDto> transactionDtoPage) {
            return builder()
                    .totalPages(transactionDtoPage.getTotalPages())
                    .totalElements(transactionDtoPage.getTotalElements())
                    .page(transactionDtoPage.getNumber() + 1)
                    .hasNext(transactionDtoPage.hasNext())
                    .hasPrevious(transactionDtoPage.hasPrevious())
                    .requestSize(transactionDtoPage.getSize())
                    .transactionSize(transactionDtoPage.getNumberOfElements())
                    .build();
        }
    }
}

