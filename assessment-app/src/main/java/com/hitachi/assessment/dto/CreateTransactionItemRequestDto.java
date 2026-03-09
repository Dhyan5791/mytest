package com.hitachi.assessment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTransactionItemRequestDto(@NotNull Integer productId,
        @NotNull @Positive BigDecimal qty) {

}
