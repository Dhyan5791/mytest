package com.hitachi.assessment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.assessment.dto.CustomerTotalSpentDto;
import com.hitachi.assessment.dto.ProductSpentDto;
import com.hitachi.assessment.dto.TaxSpentDto;
import com.hitachi.assessment.service.TransactionReportService;

@RestController
@RequestMapping("/api/reports")
public class TransactionReportController {
	private final TransactionReportService reportService;

    public TransactionReportController(TransactionReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/customers/{customerId}/total-spent")
    public CustomerTotalSpentDto totalSpentBetweenDates(
            @PathVariable Integer customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return reportService.totalSpentCustomerBetweenDates(customerId, startDate, endDate);
    }

    @GetMapping("/customers/{customerId}/total-spent/all-time")
    public CustomerTotalSpentDto totalSpentAllTime(@PathVariable Integer customerId) {
        return reportService.totalSpentCustomerAllTime(customerId);
    }

    @GetMapping("/taxes/total-spent")
    public List<TaxSpentDto> totalSpentPerTaxBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return reportService.totalSpentPerTaxBetweenDates(startDate, endDate);
    }

    @GetMapping("/products/total-spent")
    public List<ProductSpentDto> totalSpentPerProductBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return reportService.totalSpentPerProductBetweenDates(startDate, endDate);
    }
}
