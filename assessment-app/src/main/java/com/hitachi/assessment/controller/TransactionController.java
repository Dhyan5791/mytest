package com.hitachi.assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.assessment.dto.CreateTransactionRequestDto;
import com.hitachi.assessment.dto.TransactionDetailDto;
import com.hitachi.assessment.dto.TransactionResponseDto;
import com.hitachi.assessment.dto.TransactionSearchRequestDto;
import com.hitachi.assessment.dto.TransactionSummaryDto;
import com.hitachi.assessment.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponseDto create(@Valid @RequestBody CreateTransactionRequestDto req) {
        return transactionService.create(req);
    }

    @GetMapping("/{id}")
    public TransactionDetailDto getById(@PathVariable Long id) {
        return transactionService.getById(id);
    }
    
    @PostMapping("/search")
    public List<TransactionSummaryDto> search(@RequestBody TransactionSearchRequestDto req) {
        return transactionService.search(req);
    }
}
