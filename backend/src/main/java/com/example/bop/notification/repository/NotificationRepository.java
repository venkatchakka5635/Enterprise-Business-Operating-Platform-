package com.example.bop.notification.repository;

import com.example.bop.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findByTenantId(UUID tenantId, Pageable pageable);
    Page<Notification> findByRecipientId(UUID recipientId, Pageable pageable);
}
