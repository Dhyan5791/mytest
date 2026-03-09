package com.hitachi.assessment.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_method_id")
	private Integer paymentMethodId;

	@Setter
	@Column(name = "payment_method_code", nullable = false, unique = true, length = 50)
	private String paymentMethodCode;

	@Setter
	@Column(name = "is_active", nullable = false)
	private boolean active = true;

	// bi-directional
	@OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
	private Set<Transaction> transactions = new HashSet<>();

	public PaymentMethod(String paymentMethodCode, boolean active) {
		this.paymentMethodCode = paymentMethodCode;
		this.active = active;
	}
}
