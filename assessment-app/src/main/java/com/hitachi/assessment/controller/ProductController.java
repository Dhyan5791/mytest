package com.hitachi.assessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.assessment.dto.CreateProductRequestDto;
import com.hitachi.assessment.dto.ProductResponseDto;
import com.hitachi.assessment.dto.UpdateProductRequestDto;
import com.hitachi.assessment.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody CreateProductRequestDto req) {
        return productService.create(req);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Integer id,
                                     @RequestBody UpdateProductRequestDto req) {
        return productService.update(id, req);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }

    @PostMapping("/{productId}/taxes/{taxId}")
    public ProductResponseDto addTax(@PathVariable Integer productId,
                                     @PathVariable Integer taxId) {
        return productService.addTax(productId, taxId);
    }

    @DeleteMapping("/{productId}/taxes/{taxId}")
    public ProductResponseDto removeTax(@PathVariable Integer productId,
                                        @PathVariable Integer taxId) {
        return productService.removeTax(productId, taxId);
    }
}
