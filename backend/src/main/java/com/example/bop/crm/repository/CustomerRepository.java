package com.example.bop.crm.repository;

import com.example.bop.crm.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Page<Customer> findByTenantId(UUID tenantId, Pageable pageable);
    Page<Customer> findByTenantIdAndLeadStatus(UUID tenantId, String leadStatus, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.tenantId = :tenantId AND " +
           "(LOWER(c.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "c.phone LIKE CONCAT('%', :q, '%'))")
    Page<Customer> search(UUID tenantId, String q, Pageable pageable);

    long countByTenantId(UUID tenantId);
}
