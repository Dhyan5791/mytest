package com.hitachi.assessment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hitachi.assessment.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
	boolean existsByTransactionIdAndCreatedByUserId(Long transactionId, Integer createdById);
	@Query("""
	        select distinct t from Transaction t
	        left join fetch t.items i
	        left join fetch i.product
	        where t.transactionId = :id
	    """)
	    Optional<Transaction> findDetailById(@Param("id") Long id);
}
