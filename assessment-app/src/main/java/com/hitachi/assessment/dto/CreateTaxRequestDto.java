package com.hitachi.assessment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaxRequestDto(@NotBlank String taxName,
        @NotNull BigDecimal rate) {

}
