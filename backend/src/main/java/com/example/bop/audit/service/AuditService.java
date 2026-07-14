package com.example.bop.audit.service;

import com.example.bop.audit.entity.AuditLog;
import com.example.bop.audit.repository.AuditLogRepository;
import com.example.bop.common.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(String action, String entityType, UUID entityId, String oldValue, String newValue) {
        UUID tenantId = TenantContext.getTenantId();
        if (tenantId == null) return;

        AuditLog auditLog = AuditLog.builder()
                .tenantId(tenantId)
                .userId(TenantContext.getUserId())
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .build();
        auditLogRepository.save(auditLog);
        log.debug("Audit logged: {} {} {}", action, entityType, entityId);
    }

    public Page<AuditLog> getLogs(Pageable pageable) {
        return auditLogRepository.findByTenantId(TenantContext.getTenantId(), pageable);
    }
}
