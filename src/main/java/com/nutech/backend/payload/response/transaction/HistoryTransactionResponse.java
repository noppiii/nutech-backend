package com.nutech.backend.payload.response.transaction;

import com.nutech.backend.entity.Transaction;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class HistoryTransactionResponse {

    private List<TransactionResponse> transactions;
    private Pagination pagination;

    public static HistoryTransactionResponse of(Page<Transaction> transactionPage) {
        Page<TransactionResponse> transactionResponsePage = transactionPage.map(TransactionResponse::fromTransaction);  // Updated to map to TransactionResponse
        return builder()
                .transactions(transactionResponsePage.getContent())
                .pagination(Pagination.of(transactionResponsePage))
                .build();
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

        private static Pagination of(Page<TransactionResponse> transactionResponsePage) {
            return builder()
                    .totalPages(transactionResponsePage.getTotalPages())
                    .totalElements(transactionResponsePage.getTotalElements())
                    .page(transactionResponsePage.getNumber() + 1)
                    .hasNext(transactionResponsePage.hasNext())
                    .hasPrevious(transactionResponsePage.hasPrevious())
                    .requestSize(transactionResponsePage.getSize())
                    .transactionSize(transactionResponsePage.getNumberOfElements())
                    .build();
        }
    }
}
