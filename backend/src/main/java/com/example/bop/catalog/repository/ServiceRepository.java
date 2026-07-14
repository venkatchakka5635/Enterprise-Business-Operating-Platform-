package com.example.bop.catalog.repository;

import com.example.bop.catalog.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    Page<ServiceEntity> findByTenantIdAndIsLatest(UUID tenantId, Boolean isLatest, Pageable pageable);
    List<ServiceEntity> findByTenantIdAndIsLatestAndStatus(UUID tenantId, Boolean isLatest, String status);
    List<ServiceEntity> findByParentServiceIdOrderByVersionDesc(UUID parentServiceId);
}
