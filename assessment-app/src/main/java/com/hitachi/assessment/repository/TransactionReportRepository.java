package com.hitachi.assessment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hitachi.assessment.dto.CustomerTotalSpentDto;
import com.hitachi.assessment.dto.ProductSpentDto;
import com.hitachi.assessment.dto.TaxSpentDto;
import com.hitachi.assessment.entity.Transaction;

public interface TransactionReportRepository extends JpaRepository<Transaction, Long>{
	@Query("""
	        select new com.hitachi.assessment.dto.CustomerTotalSpentDto(
	            c.userId,
	            c.username,
	            coalesce(sum(t.totalAmt), 0)
	        )
	        from Transaction t
	        join t.customer c
	        where c.userId = :customerId
	          and t.transactionTime >= :startDate
	          and t.transactionTime <= :endDate
	        group by c.userId, c.username
	    """)
	    CustomerTotalSpentDto totalSpentCustomerBetweenDates(
	            @Param("customerId") Integer customerId,
	            @Param("startDate") LocalDateTime startDate,
	            @Param("endDate") LocalDateTime endDate
	    );

	    // b) total spent by customer (all history)
	    @Query("""
	        select new com.hitachi.assessment.dto.CustomerTotalSpentDto(
	            c.userId,
	            c.username,
	            coalesce(sum(t.totalAmt), 0)
	        )
	        from Transaction t
	        join t.customer c
	        where c.userId = :customerId
	        group by c.userId, c.username
	    """)
	    CustomerTotalSpentDto totalSpentCustomerAllTime(@Param("customerId") Integer customerId);

	    // d) total spent per product (net + tax + total)
	    @Query("""
	        select new com.hitachi.assessment.dto.ProductSpentDto(
	            p.productId,
	            p.productName,
	            coalesce(sum(i.qty * i.unitPrice), 0),
	            coalesce(sum(i.taxAmt), 0),
	            coalesce(sum(i.qty * i.unitPrice) + sum(i.taxAmt), 0)
	        )
	        from TransactionItem i
	        join i.transaction t
	        join i.product p
	        where t.transactionTime >= :startDate
	          and t.transactionTime <= :endDate
	        group by p.productId, p.productName
	        order by p.productName asc
	    """)
	    List<ProductSpentDto> totalSpentPerProductBetweenDates(
	            @Param("startDate") LocalDateTime startDate,
	            @Param("endDate") LocalDateTime endDate
	    );
	    
	    @Query("""
	    	    select new com.hitachi.assessment.dto.TaxSpentDto(
	    	        tx.taxId,
	    	        tx.taxName,
	    	        coalesce(sum( (i.qty * i.unitPrice) * (tx.rate / 100) ), 0)
	    	    )
	    	    from TransactionItem i
	    	    join i.transaction t
	    	    join i.product p
	    	    join p.taxes tx
	    	    where t.transactionTime >= :startDate
	    	      and t.transactionTime <= :endDate
	    	    group by tx.taxId, tx.taxName
	    	    order by tx.taxName asc
	    	""")
	    	List<TaxSpentDto> totalSpentPerTaxBetweenDates(
	    	        @Param("startDate") LocalDateTime startDate,
	    	        @Param("endDate") LocalDateTime endDate
	    	);
}
