package com.hitachi.assessment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.assessment.dto.CustomerTotalSpentDto;
import com.hitachi.assessment.dto.ProductSpentDto;
import com.hitachi.assessment.dto.TaxSpentDto;
import com.hitachi.assessment.repository.TransactionReportRepository;

@Service
public class TransactionReportService {
	private final TransactionReportRepository reportRepository;

    public TransactionReportService(TransactionReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional(readOnly = true)
    public CustomerTotalSpentDto totalSpentCustomerBetweenDates(Integer customerId,
                                                                LocalDateTime startDate,
                                                                LocalDateTime endDate) {
        return reportRepository.totalSpentCustomerBetweenDates(customerId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public CustomerTotalSpentDto totalSpentCustomerAllTime(Integer customerId) {
        return reportRepository.totalSpentCustomerAllTime(customerId);
    }

    @Transactional(readOnly = true)
    public List<TaxSpentDto> totalSpentPerTaxBetweenDates(LocalDateTime startDate,
                                                         LocalDateTime endDate) {
        return reportRepository.totalSpentPerTaxBetweenDates(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<ProductSpentDto> totalSpentPerProductBetweenDates(LocalDateTime startDate,
                                                                  LocalDateTime endDate) {
        return reportRepository.totalSpentPerProductBetweenDates(startDate, endDate);
    }
}
