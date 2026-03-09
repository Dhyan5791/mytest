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
@Table(name = "payment_status")
public class PaymentStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_status_id")
	private Integer paymentStatusId;

	@Setter
	@Column(name = "payment_status_code", nullable = false, unique = true, length = 25)
	private String paymentStatusCode;

	@Setter
	@Column(name = "is_active", nullable = false)
	private boolean active = true;

	// bi-directional
	@OneToMany(mappedBy = "paymentStatus", fetch = FetchType.LAZY)
	private Set<Transaction> transactions = new HashSet<>();

	public PaymentStatus(String paymentStatusCode, boolean active) {
		this.paymentStatusCode = paymentStatusCode;
		this.active = active;
	}
}
