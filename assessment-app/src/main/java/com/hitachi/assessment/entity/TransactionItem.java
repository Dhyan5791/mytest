package com.hitachi.assessment.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "transaction_items")
public class TransactionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_item_id")
	private Long transactionItemId;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", nullable = false)
	private Transaction transaction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Setter
	@Column(name = "qty", nullable = false, precision = 18, scale = 2)
	private BigDecimal qty;

	@Setter
	@Column(name = "unit_price", nullable = false, precision = 18, scale = 2)
	private BigDecimal unitPrice;

	@Setter
	@Column(name = "tax_amt", nullable = false, precision = 18, scale = 2)
	private BigDecimal taxAmt = BigDecimal.ZERO;

	public TransactionItem(Product product, BigDecimal qty, BigDecimal unitPrice, BigDecimal taxAmt) {
		this.product = product;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.taxAmt = taxAmt;
	}
}
