package com.hitachi.assessment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;

	// customer_id -> users
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private User customer;

	// created_by -> users
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Setter
	@Column(name = "net_amount", nullable = false, precision = 18, scale = 2)
	private BigDecimal netAmount;

	@Setter
	@Column(name = "total_tax", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalTax = BigDecimal.ZERO;

	@Setter
	@Column(name = "total_amt", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalAmt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_status_id", nullable = false)
	private PaymentStatus paymentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethod paymentMethod;

	@CreationTimestamp
	@Column(name = "transaction_time", nullable = false, updatable = false)
	private LocalDateTime transactionTime;

	@OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TransactionItem> items = new HashSet<>();

	// --- helper methods (biar bi-directional rapi) ---
	public void addItem(TransactionItem item) {
		items.add(item);
		item.setTransaction(this);
	}

	public void removeItem(TransactionItem item) {
		items.remove(item);
		item.setTransaction(null);
	}

	// --- constructor minimal ---
	public Transaction(User customer, User createdBy, PaymentStatus paymentStatus, PaymentMethod paymentMethod,
			BigDecimal netAmount, BigDecimal totalTax, BigDecimal totalAmt) {

		this.customer = customer;
		this.createdBy = createdBy;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.netAmount = netAmount;
		this.totalTax = totalTax;
		this.totalAmt = totalAmt;
	}
}
