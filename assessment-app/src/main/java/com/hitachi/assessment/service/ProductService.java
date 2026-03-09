package com.hitachi.assessment.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.assessment.dto.CreateProductRequestDto;
import com.hitachi.assessment.dto.ProductResponseDto;
import com.hitachi.assessment.dto.UpdateProductRequestDto;
import com.hitachi.assessment.entity.Product;
import com.hitachi.assessment.entity.Tax;
import com.hitachi.assessment.mapper.ProductMapper;
import com.hitachi.assessment.repository.ProductRepository;
import com.hitachi.assessment.repository.TaxRepository;

@Service
public class ProductService {
	 private final ProductRepository productRepository;
	    private final TaxRepository taxRepository;

	    public ProductService(ProductRepository productRepository,
	                          TaxRepository taxRepository) {
	        this.productRepository = productRepository;
	        this.taxRepository = taxRepository;
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @Transactional
	    public ProductResponseDto create(CreateProductRequestDto req) {

	        Product product = new Product(req.productName(), req.price());
	        Product saved = productRepository.save(product);

	        return ProductMapper.toDto(saved);
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @Transactional
	    public ProductResponseDto update(Integer productId, UpdateProductRequestDto req) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        if (req.productName() != null) {
	            product.setProductName(req.productName());
	        }

	        if (req.price() != null) {
	            product.setPrice(req.price());
	        }

	        return ProductMapper.toDto(product);
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @Transactional(readOnly = true)
	    public ProductResponseDto getById(Integer productId) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        return ProductMapper.toDto(product);
	    }

	    @Transactional(readOnly = true)
	    public List<ProductResponseDto> getAll() {
	        return productRepository.findAll()
	                .stream()
	                .map(ProductMapper::toDto)
	                .toList();
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @Transactional
	    public void delete(Integer productId) {
	        productRepository.deleteById(productId);
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    @Transactional
	    public ProductResponseDto addTax(Integer productId, Integer taxId) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        Tax tax = taxRepository.findById(taxId)
	                .orElseThrow(() -> new RuntimeException("Tax not found"));

	        product.getTaxes().add(tax);

	        return ProductMapper.toDto(product);
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @Transactional
	    public ProductResponseDto removeTax(Integer productId, Integer taxId) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        Tax tax = taxRepository.findById(taxId)
	                .orElseThrow(() -> new RuntimeException("Tax not found"));

	        product.getTaxes().remove(tax);

	        return ProductMapper.toDto(product);
	    }
}
