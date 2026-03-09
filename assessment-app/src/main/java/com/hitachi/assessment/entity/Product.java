package com.hitachi.assessment.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;

	@Setter
	@Column(name = "product_name", nullable = false)
	private String productName;

	@Setter
	@Column(name = "price", nullable = false, precision = 18, scale = 2)
	private BigDecimal price;

	// product_taxes join table
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "product_taxes", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tax_id"))
	private Set<Tax> taxes = new HashSet<>();

	// bi-directional: product dipakai di transaction_items
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Set<TransactionItem> transactionItems = new HashSet<>();

	public Product(String productName, BigDecimal price) {
		this.productName = productName;
		this.price = price;
	}
}
