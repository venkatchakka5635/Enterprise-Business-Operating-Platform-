-- V4: Dynamic Forms
CREATE TABLE form_schemas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    fields JSONB NOT NULL DEFAULT '[]',
    version INT NOT NULL DEFAULT 1,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by UUID,
    updated_by UUID
);

CREATE TABLE form_submissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    form_schema_id UUID NOT NULL REFERENCES form_schemas(id),
    submitted_by UUID,
    customer_id UUID,
    appointment_id UUID,
    data JSONB NOT NULL DEFAULT '{}',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by UUID,
    updated_by UUID
);

CREATE INDEX idx_form_schemas_tenant ON form_schemas(tenant_id);
CREATE INDEX idx_form_submissions_tenant ON form_submissions(tenant_id);
CREATE INDEX idx_form_submissions_schema ON form_submissions(form_schema_id);
CREATE INDEX idx_form_submissions_customer ON form_submissions(customer_id);
