package com.example.bop.audit.controller;

import com.example.bop.audit.entity.AuditLog;
import com.example.bop.audit.service.AuditService;
import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@Tag(name = "Audit Trail")
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "View audit logs (OWNER only)")
    public ApiResponse<PagedResponse<AuditLog>> list(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.ok(PagedResponse.from(auditService.getLogs(pageable)));
    }
}
