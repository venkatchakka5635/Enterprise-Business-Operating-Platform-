package com.example.bop.workflow.service;

import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.workflow.dto.WorkflowDTO;
import com.example.bop.workflow.entity.WorkflowDefinition;
import com.example.bop.workflow.repository.WorkflowRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final ObjectMapper objectMapper;

    public PagedResponse<WorkflowDTO> listWorkflows(Pageable pageable) {
        return PagedResponse.from(
                workflowRepository.findByTenantId(TenantContext.getTenantId(), pageable).map(this::toDTO));
    }

    public WorkflowDTO getWorkflow(UUID id) {
        return toDTO(workflowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", id)));
    }

    @Transactional
    public WorkflowDTO createWorkflow(WorkflowDTO dto) {
        WorkflowDefinition wf = WorkflowDefinition.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .stages(dto.getStages() != null ? dto.getStages() : "[]")
                .build();
        wf.setTenantId(TenantContext.getTenantId());
        return toDTO(workflowRepository.save(wf));
    }

    @Transactional
    public WorkflowDTO updateWorkflow(UUID id, WorkflowDTO dto) {
        WorkflowDefinition wf = workflowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", id));
        if (dto.getName() != null) wf.setName(dto.getName());
        if (dto.getDescription() != null) wf.setDescription(dto.getDescription());
        if (dto.getStages() != null) wf.setStages(dto.getStages());
        if (dto.getIsActive() != null) wf.setIsActive(dto.getIsActive());
        return toDTO(workflowRepository.save(wf));
    }

    /**
     * Validates a stage transition against the workflow definition.
     * Returns the new stage name if valid, throws if invalid.
     */
    public String validateTransition(UUID workflowId, String currentStage, String targetStage) {
        WorkflowDefinition wf = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", workflowId));
        try {
            List<Map<String, Object>> stages = objectMapper.readValue(wf.getStages(),
                    new TypeReference<List<Map<String, Object>>>() {});

            if (currentStage == null || currentStage.isBlank()) {
                // First stage transition — allow moving to the first stage
                if (!stages.isEmpty() && stages.get(0).get("name").toString().equals(targetStage)) {
                    return targetStage;
                }
                throw new IllegalArgumentException("Invalid initial stage: " + targetStage);
            }

            for (Map<String, Object> stage : stages) {
                if (stage.get("name").toString().equals(currentStage)) {
                    @SuppressWarnings("unchecked")
                    List<String> allowed = (List<String>) stage.get("allowedTransitions");
                    if (allowed != null && allowed.contains(targetStage)) {
                        return targetStage;
                    }
                    throw new IllegalArgumentException(
                            "Transition from '" + currentStage + "' to '" + targetStage + "' is not allowed");
                }
            }
            throw new IllegalArgumentException("Current stage '" + currentStage + "' not found in workflow");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid workflow stages configuration");
        }
    }

    private WorkflowDTO toDTO(WorkflowDefinition wf) {
        return WorkflowDTO.builder()
                .id(wf.getId()).name(wf.getName()).description(wf.getDescription())
                .stages(wf.getStages()).isActive(wf.getIsActive())
                .build();
    }
}
