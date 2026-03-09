package com.hitachi.assessment.mapper;

import com.hitachi.assessment.dto.TransactionSummaryDto;
import com.hitachi.assessment.entity.Transaction;

public class TransactionSummaryMapper {
	public static TransactionSummaryDto toDto(Transaction trx) {
        return new TransactionSummaryDto(
                trx.getTransactionId(),
                trx.getCustomer().getUserId(),
                trx.getCustomer().getUsername(),
                trx.getCreatedBy().getUserId(),
                trx.getCreatedBy().getUsername(),
                trx.getPaymentStatus().getPaymentStatusCode(),
                trx.getPaymentMethod().getPaymentMethodCode(),
                trx.getNetAmount(),
                trx.getTotalTax(),
                trx.getTotalAmt(),
                trx.getTransactionTime()
        );
    }
}
