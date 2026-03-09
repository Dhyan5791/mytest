package com.hitachi.assessment.mapper;

import java.util.stream.Collectors;

import com.hitachi.assessment.dto.TransactionDetailDto;
import com.hitachi.assessment.dto.TransactionItemResponseDto;
import com.hitachi.assessment.entity.Transaction;

public class TransactionDetailMapper {
	public static TransactionDetailDto toDto(Transaction trx) {

        return new TransactionDetailDto(
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
                trx.getTransactionTime(),
                trx.getItems().stream()
                        .map(item -> new TransactionItemResponseDto(
                                item.getTransactionItemId(),
                                item.getProduct().getProductId(),
                                item.getProduct().getProductName(),
                                item.getQty(),
                                item.getUnitPrice(),
                                item.getTaxAmt(),
                                item.getQty().multiply(item.getUnitPrice()).add(item.getTaxAmt())
                        ))
                        .collect(Collectors.toList())
        );
    }
}
