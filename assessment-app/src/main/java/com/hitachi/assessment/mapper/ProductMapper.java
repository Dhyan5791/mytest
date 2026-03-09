package com.hitachi.assessment.mapper;

import java.util.stream.Collectors;

import com.hitachi.assessment.dto.ProductResponseDto;
import com.hitachi.assessment.entity.Product;

public class ProductMapper {
	public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getTaxes()
                        .stream()
                        .map(TaxMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }
}
