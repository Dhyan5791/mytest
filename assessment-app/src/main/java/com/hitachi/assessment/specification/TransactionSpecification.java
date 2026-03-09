package com.hitachi.assessment.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hitachi.assessment.dto.TransactionSearchRequestDto;
import com.hitachi.assessment.entity.Transaction;

public class TransactionSpecification {
	public static Specification<Transaction> filter(TransactionSearchRequestDto req, boolean isAdmin, Integer currentUserId) {

        return (root, query, cb) -> {

            query.distinct(true);

            var predicates = cb.conjunction();

            // kalau bukan admin: hanya transaksi yang dia buat
            if (!isAdmin) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("createdBy").get("userId"), currentUserId)
                );
            }

            // date range
            if (req.startDate() != null) {
                predicates = cb.and(predicates,
                        cb.greaterThanOrEqualTo(root.get("transactionTime"), req.startDate())
                );
            }

            if (req.endDate() != null) {
                predicates = cb.and(predicates,
                        cb.lessThanOrEqualTo(root.get("transactionTime"), req.endDate())
                );
            }

            // customer username contains (case-insensitive)
            if (req.customerName() != null && !req.customerName().isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(
                                cb.lower(root.get("customer").get("username")),
                                "%" + req.customerName().toLowerCase() + "%"
                        )
                );
            }

            // createdBy username contains
            if (req.createdByName() != null && !req.createdByName().isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(
                                cb.lower(root.get("createdBy").get("username")),
                                "%" + req.createdByName().toLowerCase() + "%"
                        )
                );
            }

            // payment statuses in (...)
            if (req.paymentStatuses() != null && !req.paymentStatuses().isEmpty()) {
                predicates = cb.and(predicates,
                        root.get("paymentStatus").get("paymentStatusCode").in(req.paymentStatuses())
                );
            }

            // payment methods in (...)
            if (req.paymentMethods() != null && !req.paymentMethods().isEmpty()) {
                predicates = cb.and(predicates,
                        root.get("paymentMethod").get("paymentMethodCode").in(req.paymentMethods())
                );
            }

            return predicates;
        };
    }
}
