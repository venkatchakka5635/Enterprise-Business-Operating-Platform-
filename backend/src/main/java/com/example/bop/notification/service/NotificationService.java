package com.example.bop.notification.service;

import com.example.bop.common.tenant.TenantContext;
import com.example.bop.notification.entity.Notification;
import com.example.bop.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * Unified send method — currently logs to console (mock).
     * Real provider wiring is a documented follow-up.
     */
    public Notification send(String channel, String recipientEmail, String recipientPhone,
                              UUID recipientId, String subject, String body,
                              String referenceType, UUID referenceId) {
        UUID tenantId = TenantContext.getTenantId();

        Notification notification = Notification.builder()
                .tenantId(tenantId != null ? tenantId : UUID.randomUUID())
                .recipientId(recipientId)
                .recipientEmail(recipientEmail)
                .recipientPhone(recipientPhone)
                .channel(channel)
                .subject(subject)
                .body(body)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .status("SENT")
                .sentAt(LocalDateTime.now())
                .build();

        // Mock delivery
        log.info("[NOTIFICATION] channel={}, to={}, subject={}", channel,
                recipientEmail != null ? recipientEmail : recipientPhone, subject);

        notification.setDeliveredAt(LocalDateTime.now());
        notification.setStatus("DELIVERED");

        return notificationRepository.save(notification);
    }

    public Page<Notification> getNotifications(Pageable pageable) {
        return notificationRepository.findByTenantId(TenantContext.getTenantId(), pageable);
    }
}
