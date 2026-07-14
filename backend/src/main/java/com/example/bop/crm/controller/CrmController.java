package com.example.bop.crm.controller;

import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.crm.dto.CustomerDTO;
import com.example.bop.crm.entity.CustomerInteraction;
import com.example.bop.crm.entity.CustomerNote;
import com.example.bop.crm.service.CrmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "CRM")
public class CrmController {

    private final CrmService crmService;

    @GetMapping
    @Operation(summary = "List customers with search and filter")
    public ApiResponse<PagedResponse<CustomerDTO>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String leadStatus,
            @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable pageable) {
        return ApiResponse.ok(crmService.listCustomers(search, leadStatus, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer detail")
    public ApiResponse<CustomerDTO> get(@PathVariable UUID id) {
        return ApiResponse.ok(crmService.getCustomer(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a customer")
    public ApiResponse<CustomerDTO> create(@Valid @RequestBody CustomerDTO dto) {
        return ApiResponse.ok(crmService.createCustomer(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer")
    public ApiResponse<CustomerDTO> update(@PathVariable UUID id, @RequestBody CustomerDTO dto) {
        return ApiResponse.ok(crmService.updateCustomer(id, dto));
    }

    @GetMapping("/{id}/notes")
    @Operation(summary = "Get customer notes")
    public ApiResponse<List<CustomerNote>> getNotes(@PathVariable UUID id) {
        return ApiResponse.ok(crmService.getNotes(id));
    }

    @PostMapping("/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a note to a customer")
    public ApiResponse<CustomerNote> addNote(@PathVariable UUID id, @RequestBody Map<String, Object> body) {
        String content = body.get("content").toString();
        LocalDate dueDate = body.containsKey("dueDate") ? LocalDate.parse(body.get("dueDate").toString()) : null;
        boolean isFollowUp = body.containsKey("isFollowUp") && Boolean.parseBoolean(body.get("isFollowUp").toString());
        return ApiResponse.ok(crmService.addNote(id, content, dueDate, isFollowUp));
    }

    @GetMapping("/{id}/interactions")
    @Operation(summary = "Get customer interaction history")
    public ApiResponse<List<CustomerInteraction>> getInteractions(@PathVariable UUID id) {
        return ApiResponse.ok(crmService.getInteractions(id));
    }
}
