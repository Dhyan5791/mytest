package com.hitachi.assessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitachi.assessment.entity.Tax;

public interface TaxRepository extends JpaRepository<Tax, Integer>{
	Optional<Tax> findByTaxName(String taxName);
}
