package com.hitachi.assessment.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateTransactionRequestDto(@NotNull Integer customerId,
        @NotNull Integer paymentMethodId,
        @NotEmpty List<CreateTransactionItemRequestDto> items) {

}
