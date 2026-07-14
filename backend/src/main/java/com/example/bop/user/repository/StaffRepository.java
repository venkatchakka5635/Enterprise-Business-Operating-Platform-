package com.example.bop.user.repository;

import com.example.bop.user.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    Page<Staff> findByTenantId(UUID tenantId, Pageable pageable);
    Optional<Staff> findByUserId(UUID userId);
    List<Staff> findByTenantIdAndIsAvailable(UUID tenantId, Boolean isAvailable);

    @Query("SELECT s FROM Staff s WHERE s.tenantId = :tenantId AND s.id IN :staffIds")
    List<Staff> findByTenantIdAndIdIn(UUID tenantId, List<UUID> staffIds);
}
