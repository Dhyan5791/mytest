package com.hitachi.assessment.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.assessment.dto.CreateTaxRequestDto;
import com.hitachi.assessment.dto.TaxResponseDto;
import com.hitachi.assessment.dto.UpdateTaxRequestDto;
import com.hitachi.assessment.entity.Tax;
import com.hitachi.assessment.mapper.TaxMapper;
import com.hitachi.assessment.repository.TaxRepository;


@Service
public class TaxService {
	private final TaxRepository taxRepository;

    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TaxResponseDto create(CreateTaxRequestDto req) {

        if (taxRepository.findByTaxName(req.taxName()).isPresent()) {
            throw new RuntimeException("Tax name already exists");
        }

        Tax tax = new Tax(req.taxName(), req.rate());
        Tax saved = taxRepository.save(tax);

        return TaxMapper.toDto(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TaxResponseDto update(Integer taxId, UpdateTaxRequestDto req) {

        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new RuntimeException("Tax not found"));

        if (req.taxName() != null) {
            tax.setTaxName(req.taxName());
        }

        if (req.rate() != null) {
            tax.setRate(req.rate());
        }

        return TaxMapper.toDto(tax);
    }

    @Transactional(readOnly = true)
    public TaxResponseDto getById(Integer taxId) {
        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new RuntimeException("Tax not found"));

        return TaxMapper.toDto(tax);
    }

    @Transactional(readOnly = true)
    public List<TaxResponseDto> getAll() {
        return taxRepository.findAll()
                .stream()
                .map(TaxMapper::toDto)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Integer taxId) {
        taxRepository.deleteById(taxId);
    }
}
