package com.hitachi.assessment.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record TransactionSearchRequestDto(LocalDateTime startDate,
        LocalDateTime endDate,

        String customerName,     
        String createdByName,    

        Set<String> paymentStatuses, 
        Set<String> paymentMethods, 

        Boolean newestFirst) {

}
