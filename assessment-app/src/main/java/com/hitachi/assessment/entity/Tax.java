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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "taxes")
public class Tax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tax_id")
	private Integer taxId;

	@Setter
	@Column(name = "tax_name", nullable = false, unique = true)
	private String taxName;

	@Setter
	@Column(name = "rate", nullable = false, precision = 5, scale = 2)
	private BigDecimal rate;

	@ManyToMany(mappedBy = "taxes", fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<>();

	public Tax(String taxName, BigDecimal rate) {
		this.taxName = taxName;
		this.rate = rate;
	}
}
