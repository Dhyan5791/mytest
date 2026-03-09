package com.hitachi.assessment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionSummaryDto(Long transactionId,
        Integer customerId,
        String customerUsername,
        Integer createdById,
        String createdByUsername,
        String paymentStatus,
        String paymentMethod,
        BigDecimal netAmount,
        BigDecimal totalTax,
        BigDecimal totalAmt,
        LocalDateTime transactionTime) {

}
