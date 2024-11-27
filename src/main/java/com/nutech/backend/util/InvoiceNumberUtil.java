package com.nutech.backend.util;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class InvoiceNumberUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String generateInvoiceNumber(Long transactionId, LocalDateTime createdAt) {
        String dateFormatted = createdAt.format(DATE_FORMATTER);
        return "INV" + transactionId + "-" + dateFormatted;
    }
}

