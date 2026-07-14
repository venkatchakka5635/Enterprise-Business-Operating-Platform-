package com.example.bop.tenant.controller;

import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.tenant.dto.*;
import com.example.bop.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tenant & Business Profile")
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new tenant with owner account")
    public ApiResponse<Map<String, Object>> register(@Valid @RequestBody TenantRegistrationRequest request) {
        return ApiResponse.ok(tenantService.registerTenant(request), "Tenant registered successfully");
    }

    @GetMapping("/api/tenant/profile")
    @Operation(summary = "Get current tenant profile")
    public ApiResponse<TenantDTO> getProfile() {
        return ApiResponse.ok(tenantService.getTenant(TenantContext.getTenantId()));
    }

    @PutMapping("/api/tenant/profile")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Update tenant profile (branding, name)")
    public ApiResponse<TenantDTO> updateProfile(@Valid @RequestBody TenantDTO dto) {
        return ApiResponse.ok(tenantService.updateTenant(TenantContext.getTenantId(), dto));
    }

    // --- Locations ---

    @GetMapping("/api/locations")
    @Operation(summary = "List locations")
    public ApiResponse<PagedResponse<LocationDTO>> listLocations(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.ok(tenantService.getLocations(pageable));
    }

    @PostMapping("/api/locations")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Create a new location")
    public ApiResponse<LocationDTO> createLocation(@Valid @RequestBody LocationDTO dto) {
        return ApiResponse.ok(tenantService.createLocation(dto));
    }

    @PutMapping("/api/locations/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Update a location")
    public ApiResponse<LocationDTO> updateLocation(@PathVariable UUID id, @Valid @RequestBody LocationDTO dto) {
        return ApiResponse.ok(tenantService.updateLocation(id, dto));
    }
}
