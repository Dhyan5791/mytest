package com.hitachi.assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.assessment.dto.CreateTaxRequestDto;
import com.hitachi.assessment.dto.TaxResponseDto;
import com.hitachi.assessment.dto.UpdateTaxRequestDto;
import com.hitachi.assessment.service.TaxService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/taxes")
public class TaxController {
	private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @PostMapping
    public TaxResponseDto create(@Valid @RequestBody CreateTaxRequestDto req) {
        return taxService.create(req);
    }

    @PutMapping("/{id}")
    public TaxResponseDto update(@PathVariable Integer id,
                                 @RequestBody UpdateTaxRequestDto req) {
        return taxService.update(id, req);
    }

    @GetMapping("/{id}")
    public TaxResponseDto getById(@PathVariable Integer id) {
        return taxService.getById(id);
    }

    @GetMapping
    public List<TaxResponseDto> getAll() {
        return taxService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        taxService.delete(id);
    }
}
