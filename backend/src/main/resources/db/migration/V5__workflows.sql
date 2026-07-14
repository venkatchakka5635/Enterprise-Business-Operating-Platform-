-- V5: Workflow Engine
CREATE TABLE workflow_definitions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    stages JSONB NOT NULL DEFAULT '[]',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by UUID,
    updated_by UUID
);

-- stages JSONB structure:
-- [
--   {
--     "name": "Reception",
--     "order": 1,
--     "slaDurationMinutes": 10,
--     "allowedTransitions": ["Consultation"]
--   },
--   {
--     "name": "Consultation",
--     "order": 2,
--     "slaDurationMinutes": 30,
--     "allowedTransitions": ["Billing", "Follow-up"]
--   }
-- ]

CREATE INDEX idx_workflow_definitions_tenant ON workflow_definitions(tenant_id);
