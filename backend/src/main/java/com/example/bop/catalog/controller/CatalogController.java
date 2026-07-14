package com.example.bop.catalog.controller;

import com.example.bop.catalog.dto.ServiceDTO;
import com.example.bop.catalog.entity.ServiceCategory;
import com.example.bop.catalog.service.CatalogService;
import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
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
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Service Catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping
    @Operation(summary = "List services (latest versions only)")
    public ApiResponse<PagedResponse<ServiceDTO>> list(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.ok(catalogService.listServices(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get service detail")
    public ApiResponse<ServiceDTO> get(@PathVariable UUID id) {
        return ApiResponse.ok(catalogService.getService(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Create a new service")
    public ApiResponse<ServiceDTO> create(@Valid @RequestBody ServiceDTO dto) {
        return ApiResponse.ok(catalogService.createService(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Update service (creates new version)")
    public ApiResponse<ServiceDTO> update(@PathVariable UUID id, @Valid @RequestBody ServiceDTO dto) {
        return ApiResponse.ok(catalogService.updateService(id, dto));
    }

    @GetMapping("/categories")
    @Operation(summary = "List service categories")
    public ApiResponse<List<ServiceCategory>> listCategories() {
        return ApiResponse.ok(catalogService.listCategories());
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Create a category")
    public ApiResponse<ServiceCategory> createCategory(@RequestBody Map<String, String> body) {
        return ApiResponse.ok(catalogService.createCategory(body.get("name"), body.get("description")));
    }
}
