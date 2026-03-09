package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record TaxResponseDto(Integer taxId,
        String taxName,
        BigDecimal rate) {

}
