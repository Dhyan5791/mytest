package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record TransactionItemResponseDto(Long transactionItemId,
        Integer productId,
        String productName,
        BigDecimal qty,
        BigDecimal unitPrice,
        BigDecimal taxAmt,
        BigDecimal lineTotal) {

}
