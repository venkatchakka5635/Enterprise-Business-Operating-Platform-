package com.example.bop.workflow.repository;

import com.example.bop.workflow.entity.WorkflowDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<WorkflowDefinition, UUID> {
    Page<WorkflowDefinition> findByTenantId(UUID tenantId, Pageable pageable);
}
