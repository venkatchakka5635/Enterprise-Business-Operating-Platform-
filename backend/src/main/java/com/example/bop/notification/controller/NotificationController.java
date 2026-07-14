package com.example.bop.notification.controller;

import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.notification.entity.Notification;
import com.example.bop.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "List notification history")
    public ApiResponse<PagedResponse<Notification>> list(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.ok(PagedResponse.from(notificationService.getNotifications(pageable)));
    }
}
