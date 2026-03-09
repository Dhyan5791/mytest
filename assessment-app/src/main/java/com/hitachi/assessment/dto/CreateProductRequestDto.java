package com.hitachi.assessment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequestDto(@NotBlank String productName,
        @NotNull BigDecimal price) {

}
