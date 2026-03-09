package com.hitachi.assessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitachi.assessment.entity.PaymentStatus;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer>{
	Optional<PaymentStatus> findByPaymentStatusCode(String code);
	Optional<PaymentStatus> findByPaymentStatusId(Integer paymentStatusId);
}
