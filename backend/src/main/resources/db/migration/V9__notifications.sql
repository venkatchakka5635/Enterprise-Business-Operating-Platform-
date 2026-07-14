-- V9: Notifications
CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    recipient_id UUID,
    recipient_email VARCHAR(255),
    recipient_phone VARCHAR(20),
    channel VARCHAR(20) NOT NULL,
    template_name VARCHAR(100),
    subject VARCHAR(500),
    body TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    sent_at TIMESTAMP,
    delivered_at TIMESTAMP,
    failed_reason TEXT,
    reference_type VARCHAR(30),
    reference_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- channel: EMAIL, SMS, WHATSAPP, IN_APP
-- status: PENDING, SENT, DELIVERED, FAILED

CREATE TABLE notification_preferences (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    channel VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE(user_id, channel)
);

CREATE INDEX idx_notifications_tenant ON notifications(tenant_id);
CREATE INDEX idx_notifications_recipient ON notifications(recipient_id);
CREATE INDEX idx_notifications_status ON notifications(tenant_id, status);
CREATE INDEX idx_notification_prefs_user ON notification_preferences(user_id);
