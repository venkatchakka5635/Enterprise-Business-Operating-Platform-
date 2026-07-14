-- V7: CRM
CREATE TABLE customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(20),
    address TEXT,
    lead_status VARCHAR(20) NOT NULL DEFAULT 'NEW',
    source VARCHAR(50),
    tags JSONB DEFAULT '[]',
    custom_fields JSONB DEFAULT '{}',
    consent_sms BOOLEAN DEFAULT FALSE,
    consent_email BOOLEAN DEFAULT TRUE,
    consent_whatsapp BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by UUID,
    updated_by UUID
);

-- Lead status: NEW, CONTACTED, QUALIFIED, CONVERTED, LOST

CREATE TABLE customer_notes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    customer_id UUID NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    due_date DATE,
    is_follow_up BOOLEAN NOT NULL DEFAULT FALSE,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by UUID,
    updated_by UUID
);

CREATE TABLE customer_interactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    customer_id UUID NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    interaction_type VARCHAR(30) NOT NULL,
    description TEXT NOT NULL,
    reference_type VARCHAR(30),
    reference_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by UUID
);

-- interaction_type: APPOINTMENT, PAYMENT, NOTE, CALL, EMAIL, SMS, FORM_SUBMISSION

CREATE INDEX idx_customers_tenant ON customers(tenant_id);
CREATE INDEX idx_customers_email ON customers(tenant_id, email);
CREATE INDEX idx_customers_phone ON customers(tenant_id, phone);
CREATE INDEX idx_customers_lead_status ON customers(tenant_id, lead_status);
CREATE INDEX idx_customer_notes_tenant ON customer_notes(tenant_id);
CREATE INDEX idx_customer_notes_customer ON customer_notes(customer_id);
CREATE INDEX idx_customer_interactions_tenant ON customer_interactions(tenant_id);
CREATE INDEX idx_customer_interactions_customer ON customer_interactions(customer_id);
