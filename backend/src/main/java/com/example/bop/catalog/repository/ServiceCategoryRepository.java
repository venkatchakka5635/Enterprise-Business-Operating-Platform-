package com.example.bop.catalog.repository;

import com.example.bop.catalog.entity.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, UUID> {
    Page<ServiceCategory> findByTenantId(UUID tenantId, Pageable pageable);
}
