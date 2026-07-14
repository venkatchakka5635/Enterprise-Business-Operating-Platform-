package com.example.bop.workflow.controller;

import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.workflow.dto.WorkflowDTO;
import com.example.bop.workflow.service.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
@Tag(name = "Workflow Engine")
public class WorkflowController {

    private final WorkflowService workflowService;

    @GetMapping
    @Operation(summary = "List workflow definitions")
    public ApiResponse<PagedResponse<WorkflowDTO>> list(Pageable pageable) {
        return ApiResponse.ok(workflowService.listWorkflows(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get workflow detail")
    public ApiResponse<WorkflowDTO> get(@PathVariable UUID id) {
        return ApiResponse.ok(workflowService.getWorkflow(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Create workflow definition")
    public ApiResponse<WorkflowDTO> create(@Valid @RequestBody WorkflowDTO dto) {
        return ApiResponse.ok(workflowService.createWorkflow(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Update workflow definition")
    public ApiResponse<WorkflowDTO> update(@PathVariable UUID id, @Valid @RequestBody WorkflowDTO dto) {
        return ApiResponse.ok(workflowService.updateWorkflow(id, dto));
    }
}
