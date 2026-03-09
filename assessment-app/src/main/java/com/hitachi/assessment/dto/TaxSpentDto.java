package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record TaxSpentDto(Integer taxId,
        String taxName,
        BigDecimal totalTaxAmount) {

}
