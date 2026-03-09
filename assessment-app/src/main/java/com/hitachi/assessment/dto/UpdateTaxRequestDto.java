package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record UpdateTaxRequestDto(String taxName,
        BigDecimal rate) {

}
