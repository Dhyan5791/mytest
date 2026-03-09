package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record ProductSpentDto(Integer productId,
        String productName,
        BigDecimal totalNetAmount,
        BigDecimal totalTaxAmount,
        BigDecimal totalAmount) {

}
