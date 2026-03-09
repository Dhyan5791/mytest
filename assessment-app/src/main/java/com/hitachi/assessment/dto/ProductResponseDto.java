package com.hitachi.assessment.dto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductResponseDto(Integer productId,
        String productName,
        BigDecimal price,
        Set<TaxResponseDto> taxes) {

}
