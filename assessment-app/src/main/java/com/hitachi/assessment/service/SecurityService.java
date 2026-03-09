package com.hitachi.assessment.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hitachi.assessment.repository.TransactionRepository;
import com.hitachi.assessment.security.AuthUserPrincipal;

@Component("securityService")
public class SecurityService {

	private final TransactionRepository transactionRepository;

	public SecurityService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Integer currentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getPrincipal() == null)
			return null;

		Object principal = auth.getPrincipal();
		if (principal instanceof AuthUserPrincipal p) {
			return p.userId();
		}
		return null;
	}

	public boolean isOwner(Integer userId) {
		Integer current = currentUserId();
		return current != null && current.equals(userId);
	}

	public boolean isTransactionOwner(Long transactionId) {
		Integer current = currentUserId();
		if (current == null)
			return false;

		return transactionRepository.existsByTransactionIdAndCreatedByUserId(transactionId, current);
	}
	
	public boolean hasRole(String role) {

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth == null) return false;

	    return auth.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
	}
}
