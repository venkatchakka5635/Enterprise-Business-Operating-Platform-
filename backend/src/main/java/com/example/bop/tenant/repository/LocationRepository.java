package com.example.bop.tenant.repository;

import com.example.bop.tenant.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    Page<Location> findByTenantId(UUID tenantId, Pageable pageable);
    List<Location> findByTenantIdAndIsActive(UUID tenantId, Boolean isActive);
}
