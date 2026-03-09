package com.hitachi.assessment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.assessment.dto.CreateTransactionItemRequestDto;
import com.hitachi.assessment.dto.CreateTransactionRequestDto;
import com.hitachi.assessment.dto.TransactionDetailDto;
import com.hitachi.assessment.dto.TransactionResponseDto;
import com.hitachi.assessment.dto.TransactionSearchRequestDto;
import com.hitachi.assessment.dto.TransactionSummaryDto;
import com.hitachi.assessment.entity.PaymentMethod;
import com.hitachi.assessment.entity.PaymentStatus;
import com.hitachi.assessment.entity.Product;
import com.hitachi.assessment.entity.Tax;
import com.hitachi.assessment.entity.Transaction;
import com.hitachi.assessment.entity.TransactionItem;
import com.hitachi.assessment.entity.User;
import com.hitachi.assessment.mapper.TransactionDetailMapper;
import com.hitachi.assessment.mapper.TransactionMapper;
import com.hitachi.assessment.mapper.TransactionSummaryMapper;
import com.hitachi.assessment.repository.PaymentMethodRepository;
import com.hitachi.assessment.repository.PaymentStatusRepository;
import com.hitachi.assessment.repository.ProductRepository;
import com.hitachi.assessment.repository.TransactionRepository;
import com.hitachi.assessment.repository.UserRepository;
import com.hitachi.assessment.specification.TransactionSpecification;

@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final PaymentMethodRepository paymentMethodRepository;
	private final PaymentStatusRepository paymentStatusRepository;
	private final SecurityService securityService;

	public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,
			ProductRepository productRepository, PaymentMethodRepository paymentMethodRepository,
			PaymentStatusRepository paymentStatusRepository, SecurityService securityService) {
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.paymentMethodRepository = paymentMethodRepository;
		this.paymentStatusRepository = paymentStatusRepository;
		this.securityService = securityService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public TransactionResponseDto create(CreateTransactionRequestDto req) {

		Integer createdById = securityService.currentUserId();
		if (createdById == null) {
			throw new RuntimeException("Unauthorized");
		}

		User createdBy = userRepository.findById(createdById)
				.orElseThrow(() -> new RuntimeException("Creator user not found"));

		User customer = userRepository.findById(req.customerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		PaymentMethod paymentMethod = paymentMethodRepository.findById(req.paymentMethodId())
				.orElseThrow(() -> new RuntimeException("Payment method not found"));

		PaymentStatus paymentStatus = paymentStatusRepository.findByPaymentStatusCode("UNPAID")
				.orElseThrow(() -> new RuntimeException("Default payment status UNPAID not found"));

		Transaction trx = new Transaction(customer, createdBy, paymentStatus, paymentMethod, BigDecimal.ZERO,
				BigDecimal.ZERO, BigDecimal.ZERO);

		BigDecimal net = BigDecimal.ZERO;
		BigDecimal totalTax = BigDecimal.ZERO;

		for (CreateTransactionItemRequestDto itemReq : req.items()) {

			Product product = productRepository.findById(itemReq.productId())
					.orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.productId()));

			BigDecimal qty = itemReq.qty();
			BigDecimal unitPrice = product.getPrice();

			BigDecimal lineNet = unitPrice.multiply(qty);

			BigDecimal lineTax = BigDecimal.ZERO;
			for (Tax tax : product.getTaxes()) {
				BigDecimal rate = tax.getRate(); 
				BigDecimal taxValue = lineNet.multiply(rate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
				lineTax = lineTax.add(taxValue);
			}

			TransactionItem item = new TransactionItem(product, qty, unitPrice, lineTax);

			trx.addItem(item);

			net = net.add(lineNet);
			totalTax = totalTax.add(lineTax);
		}

		trx.setNetAmount(net);
		trx.setTotalTax(totalTax);
		trx.setTotalAmt(net.add(totalTax));

		Transaction saved = transactionRepository.save(trx);

		return TransactionMapper.toDto(saved);
	}

	@PreAuthorize("hasRole('ADMIN') or @securityService.isTransactionOwner(#transactionId)")
	@Transactional(readOnly = true)
	public TransactionDetailDto getById(Long transactionId) {
		Transaction trx = transactionRepository.findDetailById(transactionId)
	            .orElseThrow(() -> new RuntimeException("Transaction not found"));

	    return TransactionDetailMapper.toDto(trx);
	}
	
	@Transactional(readOnly = true)
	public List<TransactionSummaryDto> search(TransactionSearchRequestDto req) {

	    Integer currentUserId = securityService.currentUserId();
	    if (currentUserId == null) throw new RuntimeException("Unauthorized");

	    boolean isAdmin = securityService.hasRole("ADMIN");

	    Specification<Transaction> spec = TransactionSpecification.filter(req, isAdmin, currentUserId);

	    Sort sort = Sort.by("transactionTime");
	    if (req.newestFirst() != null && req.newestFirst()) {
	        sort = sort.descending();
	    } else {
	        sort = sort.ascending();
	    }

	    return transactionRepository.findAll(spec, sort)
	            .stream()
	            .map(TransactionSummaryMapper::toDto)
	            .toList();
	}
}
