package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record UpdateProductRequestDto(String productName,
        BigDecimal price) {

}
