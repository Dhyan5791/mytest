package com.hitachi.assessment.dto;

import java.math.BigDecimal;

public record CustomerTotalSpentDto(Integer customerId,
        String customerUsername,
        BigDecimal totalSpent) {

}
