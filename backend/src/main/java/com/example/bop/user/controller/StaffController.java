package com.example.bop.user.controller;

import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.user.dto.CreateStaffRequest;
import com.example.bop.user.dto.StaffDTO;
import com.example.bop.user.entity.Role;
import com.example.bop.user.repository.RoleRepository;
import com.example.bop.user.service.StaffService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Staff & Users")
public class StaffController {

    private final StaffService staffService;
    private final RoleRepository roleRepository;

    @GetMapping
    @Operation(summary = "List all staff")
    public ApiResponse<PagedResponse<StaffDTO>> list(
            @PageableDefault(sort = "user.firstName", direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.ok(staffService.listStaff(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get staff detail")
    public ApiResponse<StaffDTO> get(@PathVariable UUID id) {
        return ApiResponse.ok(staffService.getStaff(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Create a new staff member")
    public ApiResponse<StaffDTO> create(@Valid @RequestBody CreateStaffRequest request) {
        return ApiResponse.ok(staffService.createStaff(request), "Staff member created");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Update a staff member")
    public ApiResponse<StaffDTO> update(@PathVariable UUID id, @RequestBody StaffDTO dto) {
        return ApiResponse.ok(staffService.updateStaff(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Deactivate a staff member")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        staffService.deleteStaff(id);
        return ApiResponse.ok(null, "Staff member deactivated");
    }

    @GetMapping("/roles")
    @Operation(summary = "List available roles")
    public ApiResponse<List<Role>> listRoles() {
        return ApiResponse.ok(roleRepository.findByTenantId(TenantContext.getTenantId(),
                Pageable.unpaged()).getContent());
    }
}
