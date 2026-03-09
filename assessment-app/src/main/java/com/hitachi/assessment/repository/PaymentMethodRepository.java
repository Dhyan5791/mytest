package com.hitachi.assessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitachi.assessment.entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer>{
	Optional<PaymentMethod> findByPaymentMethodCode(String paymentMethodCode);
	Optional<PaymentMethod> findByPaymentMethodId(Integer paymentMethodId);
}
