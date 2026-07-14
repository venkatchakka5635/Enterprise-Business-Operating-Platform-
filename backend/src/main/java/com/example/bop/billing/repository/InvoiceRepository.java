package com.example.bop.billing.repository;

import com.example.bop.billing.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    Page<Invoice> findByTenantId(UUID tenantId, Pageable pageable);
    Page<Invoice> findByTenantIdAndStatus(UUID tenantId, String status, Pageable pageable);
    Page<Invoice> findByTenantIdAndCustomerId(UUID tenantId, UUID customerId, Pageable pageable);
    long countByTenantId(UUID tenantId);
    long countByTenantIdAndStatus(UUID tenantId, String status);

    @Query("SELECT COALESCE(SUM(i.total), 0) FROM Invoice i WHERE i.tenantId = :tenantId AND i.status = 'PAID' " +
           "AND i.updatedAt >= :start AND i.updatedAt < :end")
    BigDecimal sumRevenueByPeriod(UUID tenantId, LocalDateTime start, LocalDateTime end);
}
