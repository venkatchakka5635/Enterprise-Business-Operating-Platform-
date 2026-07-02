<p align="center">
  <strong>EBOP</strong>
</p>

<h3 align="center">Enterprise Business Operating Platform</h3>

<p align="center">
  A cloud-native, metadata-driven, multi-tenant SaaS platform that enables<br/>
  service-based businesses to configure and operate their entire business<br/>
  without writing a single line of code.
</p>

<p align="center">
  <a href="#getting-started">Getting Started</a> |
  <a href="#architecture">Architecture</a> |
  <a href="#platform-modules">Modules</a> |
  <a href="#technology-stack">Tech Stack</a> |
  <a href="#roadmap">Roadmap</a> |
  <a href="#contributing">Contributing</a>
</p>

---

## Product Vision

EBOP exists to solve a fundamental inefficiency in the service industry: every clinic, salon, hospital, consulting firm, and wellness center runs on the same operational primitives --- appointments, services, customers, billing, staff, and workflows --- yet each is forced to either adopt rigid off-the-shelf software or invest in expensive custom development.

EBOP is the operating system for service-based businesses.

A single platform where any service organization can configure its own workflows, forms, roles, permissions, booking rules, CRM, billing, customer portal, and even its public-facing website --- entirely through an administrative interface. No developers. No custom code. No vendor lock-in.

The platform is not a template. It is a configuration engine. The metadata layer defines the behavior. The runtime interprets it. The result is a system that adapts to the business, not the other way around.

---

## Executive Summary

| Dimension | Detail |
|---|---|
| **Product Category** | Horizontal SaaS Platform (PaaS for service businesses) |
| **Deployment Model** | Cloud-native, multi-tenant, SaaS-first |
| **Core Innovation** | Metadata-driven configuration engine with runtime interpretation |
| **Target Market** | Service-based businesses across healthcare, wellness, professional services, and education |
| **Architecture** | Event-driven microservices with tenant-isolated data and shared compute |
| **Backend** | Spring Boot 4.1, PostgreSQL, Redis, Elasticsearch |
| **Frontend** | React 19, TypeScript, Vite |
| **Estimated TAM** | 50M+ service businesses globally lacking purpose-built operating platforms |

---

## Why EBOP Exists

### The Problem

The service industry is the largest employment sector globally, yet its technology landscape is fragmented and underserved:

1. **Vertical Fragmentation.** A dermatology clinic uses one system, a physiotherapy center uses another, and a salon uses a third. All three need the same core capabilities: appointments, billing, customer management, and staff scheduling. The differences are in configuration, not in architecture.

2. **Build vs. Buy Trap.** Businesses that outgrow generic tools face a binary choice: adapt their processes to the software, or commission custom development. Both are expensive. Both are slow. Both create technical debt.

3. **Integration Tax.** The average service business uses 4 to 7 disconnected tools for scheduling, invoicing, CRM, marketing, and communication. Data silos form. Operational visibility degrades. Staff spend time on data entry instead of service delivery.

4. **Scaling Rigidity.** A single-location physiotherapy practice that grows into a multi-city chain cannot scale its operational software without a migration. Systems designed for one location break when applied to ten.

5. **Compliance Blind Spots.** Healthcare, legal, and financial service providers operate under regulatory frameworks that demand audit trails, data isolation, consent management, and access controls. Most SMB software treats compliance as an afterthought.

### The Insight

The operational backbone of every service business can be decomposed into a finite set of configurable primitives:

- **Services** that are offered
- **Resources** (staff, rooms, equipment) that deliver those services
- **Appointments** that match demand to supply
- **Workflows** that govern operational processes
- **Forms** that capture structured data
- **Roles and Permissions** that enforce access control
- **Billing** that monetizes service delivery
- **Communication** that maintains customer relationships

If these primitives are metadata-driven rather than hard-coded, a single platform can serve any service vertical without code changes.

EBOP is that platform.

---

## Problems We Solve

| Problem | EBOP Solution |
|---|---|
| Separate software for every vertical | One configurable platform for all service businesses |
| Rigid workflows that do not match real processes | Tenant-defined workflow engine with conditional branching |
| Generic forms that miss critical data | Dynamic form builder with field-level validation and conditional logic |
| No visibility across locations | Multi-location hierarchy with consolidated dashboards and reports |
| Integration overhead | Unified data model with built-in modules for CRM, billing, and communication |
| Compliance gaps | Tenant-isolated data, audit logging, role-based access, and consent management |
| Scaling requires re-platforming | Multi-tenant architecture designed for growth from day one |
| Vendor lock-in | Open data model, API-first design, standard export formats |

---

## Core Philosophy

### 1. Configuration Over Code

Every behavior in the system is driven by metadata. Adding a new service, form field, workflow step, user role, or booking rule is a configuration change, not a code deployment.

### 2. Tenant Autonomy

Each tenant operates in a logically isolated environment with full control over their configuration. One tenant's customization never affects another tenant's behavior or performance.

### 3. Composable Primitives

The platform is built from composable building blocks --- services, forms, workflows, rules, roles --- that tenants assemble into their unique operational model. The same primitives that power a dental clinic can be reconfigured to power a law firm.

### 4. API-First Design

Every capability exposed through the UI is backed by a documented REST API. Third-party integrations, mobile applications, and custom frontends are first-class citizens.

### 5. Data Sovereignty

Tenant data is logically isolated at the database level. Every query is scoped to the requesting tenant. Cross-tenant data access is architecturally impossible without explicit federation.

### 6. Progressive Complexity

A sole proprietor running a massage practice and a hospital chain with 200 locations use the same platform. Complexity is additive, not mandatory. Features activate as tenants grow.

---

## Product Overview

EBOP is a horizontally scalable SaaS platform composed of modular engines that tenants configure through an administrative interface. The platform serves two primary user groups:

**Tenant Administrators** configure the platform to match their business: defining services, building forms, designing workflows, setting up roles, and managing billing. They require zero technical knowledge.

**End Users** --- staff, practitioners, front-desk operators, and customers --- interact with the configured platform through role-appropriate interfaces. A receptionist sees the booking calendar. A doctor sees patient intake forms. A customer sees the self-service portal.

The administrative layer and the operational layer share a single runtime. Configuration changes propagate in real time. There is no deployment step. There is no downtime.

---

## Key Features

### Dynamic Configuration Engine
- Metadata-driven behavior for all platform modules
- Real-time configuration propagation without deployment
- Version-controlled configuration with rollback capability
- Configuration templates for rapid tenant onboarding
- Import and export of configuration snapshots

### Multi-Tenant Architecture
- Logical tenant isolation at the data layer
- Shared compute with per-tenant resource quotas
- Tenant-aware request routing and query scoping
- Independent configuration namespaces per tenant
- Cross-tenant analytics for platform operators (super-admin)

### Dynamic Service Catalog
- Configurable service definitions with custom attributes
- Service categorization, tagging, and search
- Pricing models: fixed, tiered, duration-based, package-based, subscription
- Service dependencies and prerequisites
- Service availability rules by location, staff, and time

### Dynamic Form Engine
- Drag-and-drop form builder
- 20+ field types including text, number, date, file upload, signature, consent, and repeatable groups
- Conditional field visibility and validation rules
- Form versioning with data migration support
- Pre-fill logic from existing records
- Multi-step forms with progress tracking
- HIPAA-aware field-level encryption for sensitive data

### Workflow Engine
- Visual workflow designer with drag-and-drop canvas
- Trigger types: event-based, time-based, manual, conditional
- Action types: send notification, update record, assign task, create appointment, invoke webhook, execute approval
- Parallel and sequential execution paths
- Conditional branching with expression evaluation
- SLA tracking and escalation rules
- Workflow templates for common business processes

### Appointment Engine
- Multi-resource scheduling: staff, rooms, equipment
- Configurable booking rules: lead time, cancellation window, buffer time, max per day
- Recurring appointment support
- Waitlist management
- Automated reminders via email, SMS, and push
- Calendar sync with Google Calendar and Outlook
- Customer self-service booking through the portal
- Multi-location scheduling with timezone support

### Customer Relationship Management (CRM)
- 360-degree customer profiles with interaction history
- Custom fields and segments
- Lead pipeline management
- Automated follow-up workflows
- Customer tagging and scoring
- Communication log: calls, emails, messages, visits
- Merge and deduplication tooling
- Referral tracking

### Billing and Invoicing
- Configurable tax rules by jurisdiction
- Invoice generation with customizable templates
- Payment gateway integration: Stripe, Razorpay, PayPal
- Partial payments, deposits, and refunds
- Package and membership billing
- Recurring billing with dunning management
- Financial reporting and reconciliation
- Multi-currency support

### Notification Engine
- Multi-channel delivery: email, SMS, push notification, in-app, WhatsApp
- Template engine with variable interpolation
- Trigger-based automation via the workflow engine
- Delivery tracking and retry logic
- Tenant-configurable notification preferences
- Batch and scheduled notifications
- Unsubscribe and consent management

### User and Role Management
- Dynamic role creation with granular permissions
- Field-level and record-level access control
- Multi-level organizational hierarchy
- SSO integration: SAML 2.0, OAuth 2.0, OpenID Connect
- MFA support
- Session management and device tracking
- Invitation-based onboarding with role assignment
- Delegated administration for multi-location tenants

### Dynamic Dashboard and Reporting
- Drag-and-drop dashboard builder
- Pre-built widget library: KPIs, charts, tables, calendars, task lists
- Custom report builder with filters, grouping, and aggregation
- Scheduled report delivery via email
- Export to PDF, CSV, and Excel
- Role-scoped dashboards: each role sees only relevant metrics
- Real-time data refresh

### Customer Portal
- White-labeled, tenant-branded portal
- Self-service appointment booking and management
- Document upload and form submission
- Invoice viewing and online payment
- Communication with service providers
- Appointment history and upcoming schedule
- Configurable portal sections per tenant

### Dynamic Website Builder
- Tenant-configurable public website
- Page builder with pre-designed sections: hero, services, team, testimonials, contact, FAQ
- SEO configuration: meta tags, structured data, sitemap
- Custom domain mapping
- Responsive design out of the box
- Integration with the booking engine for online scheduling
- Blog and content management

### Document Management
- Structured document storage per customer and per appointment
- Version control for uploaded documents
- Access control aligned with user roles
- Document templates with auto-fill from system data
- Digital signature integration
- Retention policies and automated archival

### Inventory and Resource Management
- Product and consumable tracking
- Stock level alerts and reorder triggers
- Usage tracking tied to services and appointments
- Multi-location inventory visibility
- Vendor management
- Purchase order generation

### Audit and Compliance
- Comprehensive audit trail for all data mutations
- Immutable event log with timestamp, actor, and diff
- Configurable data retention policies
- GDPR data export and erasure workflows
- HIPAA-compatible access logging for healthcare tenants
- SOC 2 aligned security controls

---

## Platform Modules

```
+------------------------------------------------------------------+
|                        EBOP PLATFORM                              |
+------------------------------------------------------------------+
|                                                                    |
|  +------------------+  +------------------+  +------------------+ |
|  | Configuration    |  | Workflow         |  | Form             | |
|  | Engine           |  | Engine           |  | Engine           | |
|  +------------------+  +------------------+  +------------------+ |
|                                                                    |
|  +------------------+  +------------------+  +------------------+ |
|  | Appointment      |  | CRM              |  | Billing          | |
|  | Engine           |  | Module           |  | Module           | |
|  +------------------+  +------------------+  +------------------+ |
|                                                                    |
|  +------------------+  +------------------+  +------------------+ |
|  | Notification     |  | User & Role      |  | Dashboard &      | |
|  | Engine           |  | Management       |  | Reporting        | |
|  +------------------+  +------------------+  +------------------+ |
|                                                                    |
|  +------------------+  +------------------+  +------------------+ |
|  | Customer         |  | Website          |  | Document         | |
|  | Portal           |  | Builder          |  | Management       | |
|  +------------------+  +------------------+  +------------------+ |
|                                                                    |
|  +------------------+  +------------------+  +------------------+ |
|  | Inventory &      |  | Audit &          |  | Integration      | |
|  | Resources        |  | Compliance       |  | Gateway          | |
|  +------------------+  +------------------+  +------------------+ |
|                                                                    |
+------------------------------------------------------------------+
|                     SHARED INFRASTRUCTURE                          |
|  +------+ +-------+ +--------+ +----------+ +--------+ +------+  |
|  | Auth | | Event | | Cache  | | Search   | | File   | | API  |  |
|  |      | | Bus   | |        | | Index    | | Store  | | GW   |  |
|  +------+ +-------+ +--------+ +----------+ +--------+ +------+  |
+------------------------------------------------------------------+
```

---

## Industry Use Cases

### Healthcare

| Capability | Configuration |
|---|---|
| Patient intake | Dynamic forms with medical history, allergies, medications, consent |
| Appointment scheduling | Multi-provider, multi-room scheduling with buffer times |
| Clinical workflows | Treatment plans with step-by-step protocols and approval gates |
| Billing | Insurance-aware invoicing, copay tracking, claim generation |
| Compliance | HIPAA-aligned audit logging, field-level encryption, consent management |
| Patient portal | Self-service appointment booking, document upload, prescription history |

### Wellness and Beauty (Spas, Salons)

| Capability | Configuration |
|---|---|
| Service menu | Dynamic catalog with duration, pricing, staff assignment |
| Online booking | Customer-facing booking with real-time availability |
| Membership | Package and subscription billing with visit tracking |
| Staff management | Commission tracking, shift scheduling, performance dashboards |
| CRM | Customer preferences, visit history, automated birthday promotions |
| Retail | Product inventory tied to service consumption |

### Professional Services (Consulting, Legal, Accounting)

| Capability | Configuration |
|---|---|
| Client onboarding | Multi-step intake forms with document collection |
| Matter/Case management | Workflow-driven case lifecycle with status tracking |
| Time tracking | Billable hours linked to clients and matters |
| Engagement letters | Document templates with auto-fill and digital signature |
| Billing | Hourly, fixed-fee, retainer, and milestone-based billing |
| Client portal | Secure document exchange and status visibility |

### Education and Training

| Capability | Configuration |
|---|---|
| Enrollment | Dynamic registration forms with conditional fields |
| Class scheduling | Recurring sessions with instructor and room assignment |
| Attendance | Digital check-in with automated notifications for absences |
| Progress tracking | Custom forms for assessments and evaluations |
| Fee management | Term-based billing with installment plans |
| Parent portal | Attendance, grades, and communication in one view |

### Diagnostic Labs

| Capability | Configuration |
|---|---|
| Test catalog | Dynamic test definitions with sample requirements and turnaround times |
| Sample tracking | Workflow-driven sample lifecycle: collection, processing, reporting |
| Report generation | Template-based result reports with approval workflows |
| Referring physician portal | Report delivery and order management |
| Billing | Test-level pricing with panel discounts and insurance mapping |

---

## How the Platform Works

### Tenant Lifecycle

```
1. ONBOARDING
   Tenant signs up --> Platform provisions isolated namespace
   --> Admin receives credentials --> Guided setup wizard launches

2. CONFIGURATION
   Admin defines:
   - Services and pricing
   - Booking rules and availability
   - Forms and data capture requirements
   - Workflows and automation rules
   - Roles and permissions
   - Notification templates
   - Billing configuration
   - Customer portal settings
   - Public website content

3. OPERATION
   Staff and customers use the configured platform:
   - Front desk manages appointments
   - Practitioners complete forms and workflows
   - Customers book through the portal
   - System sends automated notifications
   - Billing generates invoices
   - Dashboards display real-time metrics

4. GROWTH
   Tenant adds locations, staff, and services
   --> Configuration scales without migration
   --> Cross-location reporting consolidates data
   --> New locations inherit base configuration
```

### Configuration Flow

```
Admin Action (UI)
      |
      v
Configuration API (validated, versioned)
      |
      v
Metadata Store (PostgreSQL JSONB)
      |
      v
Configuration Cache (Redis)
      |
      v
Runtime Engines interpret metadata at request time
      |
      v
Tenant-specific behavior without code changes
```

---

## Architecture

### High-Level Architecture

```
                          +-------------------+
                          |   Load Balancer   |
                          |   (NGINX / ALB)   |
                          +--------+----------+
                                   |
                    +--------------+--------------+
                    |                              |
          +---------+--------+          +---------+--------+
          |  Frontend (React)|          |   API Gateway    |
          |  SPA served via  |          |  Rate Limiting   |
          |  CDN / Static    |          |  Auth Validation |
          +------------------+          |  Tenant Routing  |
                                        +--------+---------+
                                                 |
                          +----------------------+----------------------+
                          |                      |                      |
                +---------+------+    +----------+-----+    +-----------+----+
                | Core API       |    | Config API     |    | Async Workers  |
                | (Spring Boot)  |    | (Spring Boot)  |    | (Spring Boot)  |
                | Appointments   |    | Metadata CRUD  |    | Notifications  |
                | CRM, Billing   |    | Form Builder   |    | Report Gen     |
                | Workflows      |    | Workflow Design|    | Scheduled Jobs |
                +-------+--------+    +-------+--------+    +-------+--------+
                        |                     |                      |
           +------------+---------------------+----------------------+------+
           |                                                                |
   +-------+--------+    +-----------+    +-----------+    +---------------+
   | PostgreSQL      |    | Redis     |    | Elastic   |    | Object Store  |
   | (Primary Store) |    | (Cache +  |    | Search    |    | (S3 / MinIO)  |
   | Tenant-scoped   |    |  Pub/Sub) |    | (Search + |    | (Documents +  |
   | schemas / RLS   |    |           |    |  Logs)    |    |  Media)       |
   +-----------------+    +-----------+    +-----------+    +---------------+
```

### Multi-Tenant Data Isolation

EBOP implements tenant isolation using a shared-database, schema-isolated model with Row-Level Security (RLS) as a defense-in-depth mechanism:

```
PostgreSQL Instance
├── Schema: tenant_001
│   ├── services
│   ├── appointments
│   ├── customers
│   ├── forms
│   ├── workflows
│   └── ...
├── Schema: tenant_002
│   ├── services
│   ├── appointments
│   └── ...
├── Schema: platform_shared
│   ├── tenants
│   ├── plans
│   ├── billing
│   └── audit_log
└── Row-Level Security policies enforce tenant_id scoping
```

**Isolation guarantees:**
- Every API request carries a tenant context extracted from the JWT
- All database queries are automatically scoped to the tenant's schema
- Cross-tenant queries are rejected at the ORM filter level before reaching the database
- Row-Level Security acts as a database-level failsafe
- Tenant schema migrations are managed independently

### Request Lifecycle

```
1. Client sends request with Bearer token
2. API Gateway validates JWT, extracts tenant_id and user_id
3. Tenant context is set on the request thread (ThreadLocal / SecurityContext)
4. Spring JPA filter automatically scopes all queries to tenant schema
5. Configuration cache is checked for tenant-specific metadata
6. Business logic executes using tenant configuration
7. Audit log entry is written
8. Response is returned with appropriate cache headers
```

---

## Dynamic Configuration Engine

The Configuration Engine is the architectural core of EBOP. It is the layer that makes the platform metadata-driven rather than code-driven.

### How It Works

Every configurable behavior in the platform is represented as a metadata record stored in PostgreSQL using JSONB columns. The metadata schema is typed and validated against JSON Schema definitions before persistence.

At runtime, when any engine (appointments, workflows, forms, billing) needs to determine behavior, it reads from the configuration cache (Redis) rather than executing hard-coded logic.

### Configuration Categories

| Category | Examples |
|---|---|
| **Service Configuration** | Service definitions, pricing rules, duration, resource requirements |
| **Form Configuration** | Field definitions, validation rules, conditional logic, layout |
| **Workflow Configuration** | Trigger conditions, action sequences, branching rules, SLA definitions |
| **Booking Configuration** | Availability windows, buffer times, cancellation policies, capacity limits |
| **Role Configuration** | Permission matrices, field-level access, menu visibility |
| **Notification Configuration** | Templates, channel preferences, trigger mappings |
| **Billing Configuration** | Tax rules, payment terms, invoice templates, gateway settings |
| **Portal Configuration** | Branding, enabled sections, custom fields |
| **Website Configuration** | Pages, sections, content, SEO metadata, domain mapping |

### Configuration Versioning

Every configuration change creates a new version. The platform maintains a full history of configuration states, enabling:

- **Rollback**: Revert to any previous configuration version
- **Diff**: Compare two configuration versions to see what changed
- **Audit**: Track who changed what, when, and why
- **Branching**: Preview configuration changes in a staging environment before applying to production

---

## Workflow Engine

The Workflow Engine enables tenants to automate business processes without code. It supports both simple linear workflows and complex multi-path processes with conditional branching, parallel execution, and human-in-the-loop approvals.

### Workflow Components

```
+-------------------+     +-------------------+     +-------------------+
|    TRIGGERS        |     |    CONDITIONS      |     |    ACTIONS         |
|--------------------|     |--------------------|     |--------------------|
| Record Created     |     | Field Value Check  |     | Send Notification  |
| Record Updated     |     | Role Check         |     | Update Record      |
| Appointment Booked |     | Time-based Check   |     | Create Task        |
| Form Submitted     |     | Custom Expression  |     | Assign Staff       |
| Payment Received   |     | Status Check       |     | Generate Document  |
| Time Elapsed       |     | Logical AND / OR   |     | Invoke Webhook     |
| Manual Trigger     |     |                    |     | Start Sub-workflow |
| Webhook Received   |     |                    |     | Request Approval   |
+-------------------+     +-------------------+     +-------------------+
```

### Example: Patient Visit Workflow (Healthcare Clinic)

```
Patient Checks In (Trigger)
      |
      v
Auto-assign to Doctor (Action)
      |
      v
Send Intake Form to Patient Device (Action)
      |
      v
Wait for Form Submission (Condition)
      |
      +---> Form Submitted
      |         |
      |         v
      |    Doctor Reviews Form (Task)
      |         |
      |         v
      |    Consultation Completed (Condition)
      |         |
      |         v
      |    Generate Invoice (Action)
      |         |
      |         v
      |    Send Payment Link (Action)
      |         |
      |         v
      |    Send Follow-up Reminder in 7 days (Scheduled Action)
      |
      +---> Form Not Submitted in 15 min
                |
                v
           Send Reminder Notification (Action)
```

---

## Dynamic Form Engine

The Form Engine allows tenants to create unlimited custom forms for data capture, intake, assessments, surveys, and operational records.

### Supported Field Types

| Category | Field Types |
|---|---|
| **Text** | Short text, long text, rich text, email, phone, URL |
| **Numeric** | Integer, decimal, currency, percentage, slider |
| **Selection** | Dropdown, radio, checkbox, multi-select, toggle |
| **Date/Time** | Date, time, datetime, date range |
| **Media** | File upload, image upload, camera capture |
| **Specialized** | Signature pad, consent checkbox, rating, NPS |
| **Layout** | Section header, divider, instructions, repeatable group |
| **Relational** | Customer lookup, staff lookup, service lookup |

### Form Capabilities

- **Conditional Logic**: Show or hide fields based on other field values
- **Validation Rules**: Required, min/max, regex, custom expressions
- **Calculated Fields**: Auto-compute values from other fields
- **Pre-fill**: Populate fields from existing customer or appointment data
- **Multi-step**: Break long forms into wizard-style pages with progress tracking
- **Versioning**: Update form definitions without losing historical submissions
- **PDF Generation**: Auto-generate PDF from form submissions
- **API Submission**: Accept form data via API for integrations

---

## Appointment Engine

### Scheduling Model

The Appointment Engine implements a constraint-based scheduling model where availability is computed dynamically from the intersection of:

1. **Staff schedules**: Working hours, breaks, days off, vacations
2. **Room/resource availability**: Room capacity, equipment schedules
3. **Service duration**: Base duration plus buffer time
4. **Booking rules**: Minimum lead time, maximum advance booking, cancellation window
5. **Existing appointments**: Conflict detection across all resources
6. **Tenant-specific overrides**: Holiday schedules, special hours, blackout dates

### Booking Flow

```
Customer selects service
      |
      v
System identifies required resources (staff + room + equipment)
      |
      v
System computes available slots from constraint intersection
      |
      v
Customer selects slot
      |
      v
System validates against booking rules
      |
      v
Appointment created --> Confirmation sent
      |
      v
Reminder workflow triggered (configurable timing)
      |
      v
Check-in --> Service delivery --> Checkout --> Invoice
```

---

## CRM

The CRM module provides a unified view of every customer interaction across all touchpoints. Unlike standalone CRM products, EBOP's CRM is natively integrated with appointments, billing, forms, and workflows.

### Customer Profile

```
+------------------------------------------------------------------+
| CUSTOMER: Jane Smith                                               |
+------------------------------------------------------------------+
| Contact Information        | Tags: VIP, Loyalty-Gold              |
| Email: jane@email.com      | Segment: High-Value Recurring        |
| Phone: +1-555-0123         | Lifetime Value: $4,850               |
| DOB: 1988-03-15            | Last Visit: 2026-06-28               |
+------------------------------------------------------------------+
| APPOINTMENTS (24)          | FORMS (8)          | INVOICES (22)   |
| Next: Jul 5, 10:00 AM     | Last: Intake v2    | Outstanding: $0 |
| Provider: Dr. Martinez     | Status: Complete   | Credits: $200   |
+------------------------------------------------------------------+
| COMMUNICATION LOG                                                  |
| Jun 28 - Visit completed, follow-up scheduled                     |
| Jun 25 - Reminder SMS sent                                        |
| Jun 20 - Appointment booked via portal                            |
| Jun 15 - Marketing email: Summer promotion                        |
+------------------------------------------------------------------+
| CUSTOM FIELDS (Tenant-defined)                                     |
| Preferred Therapist: Sarah Johnson                                 |
| Allergies: Latex                                                   |
| Membership: Premium Annual                                         |
+------------------------------------------------------------------+
```

---

## Billing

### Pricing Models

| Model | Description | Use Case |
|---|---|---|
| **Fixed** | Static price per service | Standard service pricing |
| **Duration-based** | Price per unit of time | Consulting, therapy sessions |
| **Tiered** | Price varies by quantity or volume | Lab tests, bulk bookings |
| **Package** | Bundle of services at a discounted rate | Wellness packages, treatment plans |
| **Subscription** | Recurring billing on a schedule | Memberships, retainers |
| **Milestone** | Payment tied to project milestones | Consulting engagements |
| **Deposit + Balance** | Upfront deposit with remainder on completion | High-value services |

### Billing Pipeline

```
Service Delivered
      |
      v
Invoice Generated (from template + pricing rules + tax rules)
      |
      v
Payment Link Sent (email / SMS / portal)
      |
      v
Payment Processed (Stripe / Razorpay / PayPal)
      |
      v
Receipt Generated
      |
      v
Revenue Recorded --> Financial Reports Updated
      |
      v
If overdue: Dunning workflow triggered
```

---

## Notification Engine

### Channel Matrix

| Channel | Transactional | Marketing | Reminders | Alerts |
|---|---|---|---|---|
| Email | Yes | Yes | Yes | Yes |
| SMS | Yes | Opt-in | Yes | Yes |
| Push Notification | Yes | Opt-in | Yes | Yes |
| In-App | Yes | Yes | Yes | Yes |
| WhatsApp | Yes | Opt-in | Yes | Yes |

### Template System

Notification templates support variable interpolation from any data entity in the system:

```
Subject: Appointment Confirmation - {{service.name}}

Hi {{customer.firstName}},

Your appointment for {{service.name}} has been confirmed.

Date: {{appointment.date | format: 'MMMM dd, yyyy'}}
Time: {{appointment.time | format: 'hh:mm a'}}
Provider: {{appointment.staff.name}}
Location: {{appointment.location.name}}

{{#if appointment.requiresPreparation}}
Please review the preparation instructions: {{service.preparationNotes}}
{{/if}}

To reschedule or cancel, visit: {{portal.url}}/appointments/{{appointment.id}}
```

---

## User and Role Management

### Permission Model

EBOP implements a hierarchical, attribute-based access control (ABAC) model that extends traditional RBAC:

```
Permission = Role + Resource + Action + Conditions

Example:
  Role: Front Desk
  Resource: Appointments
  Actions: Create, Read, Update
  Conditions: Only for assigned location, only for future dates

Example:
  Role: Doctor
  Resource: Patient Forms
  Actions: Read, Update
  Conditions: Only for patients assigned to this doctor
```

### Organizational Hierarchy

```
Organization (Tenant)
├── Region: North America
│   ├── Location: New York Clinic
│   │   ├── Department: Dermatology
│   │   │   ├── Dr. Smith (Provider)
│   │   │   └── Jane (Assistant)
│   │   └── Department: General Medicine
│   │       └── Dr. Johnson (Provider)
│   └── Location: Boston Clinic
│       └── Department: Dermatology
│           └── Dr. Williams (Provider)
└── Region: Europe
    └── Location: London Clinic
        └── ...
```

Role permissions cascade through the hierarchy. A regional manager sees data for all locations in their region. A location manager sees only their location. A practitioner sees only their assigned patients.

---

## Technology Stack

### Backend

| Component | Technology | Rationale |
|---|---|---|
| **Runtime** | Java 17, Spring Boot 4.1 | Enterprise-grade, mature ecosystem, strong typing |
| **API Layer** | Spring WebMVC, REST | Standard, well-documented, tooling-rich |
| **Data Access** | Spring Data JPA, Hibernate | Proven ORM with multi-tenancy support |
| **Security** | Spring Security, JWT, OAuth 2.0 | Industry-standard authentication and authorization |
| **Validation** | Spring Validation, Bean Validation | Declarative input validation |
| **Database** | PostgreSQL 16+ | JSONB for metadata, RLS for tenant isolation, proven scale |
| **Cache** | Redis 7+ | Configuration caching, session store, pub/sub for events |
| **Search** | Elasticsearch 8+ | Full-text search, log aggregation, analytics |
| **File Storage** | MinIO / AWS S3 | Scalable object storage for documents and media |
| **Message Queue** | RabbitMQ / Apache Kafka | Async processing, event-driven workflows |
| **Monitoring** | Spring Actuator, Micrometer, Prometheus | Health checks, metrics, observability |
| **API Docs** | SpringDoc OpenAPI 3.0 | Auto-generated, interactive API documentation |

### Frontend

| Component | Technology | Rationale |
|---|---|---|
| **Framework** | React 19 | Component model, ecosystem, performance |
| **Language** | TypeScript 6 | Type safety, developer productivity, refactoring confidence |
| **Build Tool** | Vite 8 | Fast development builds, optimized production bundles |
| **State Management** | Zustand / React Query | Lightweight, server-state focused |
| **UI Components** | Custom design system | Consistent, tenant-brandable components |
| **Forms** | React Hook Form + Zod | Dynamic form rendering with schema-driven validation |
| **Routing** | React Router 7 | Declarative routing with lazy loading |
| **Charts** | Recharts / Apache ECharts | Dashboard visualizations |

### Infrastructure

| Component | Technology | Rationale |
|---|---|---|
| **Containerization** | Docker | Consistent environments, deployment portability |
| **Orchestration** | Kubernetes | Auto-scaling, self-healing, rolling deployments |
| **CI/CD** | GitHub Actions | Automated testing, building, and deployment |
| **CDN** | CloudFront / Cloudflare | Static asset delivery, edge caching |
| **DNS** | Route 53 / Cloudflare | Custom domain mapping for tenant websites |
| **SSL** | Let's Encrypt, cert-manager | Automated TLS certificate provisioning |
| **Logging** | ELK Stack (Elasticsearch, Logstash, Kibana) | Centralized logging with tenant-scoped views |
| **APM** | Grafana + Prometheus | Performance monitoring and alerting |

---

## Security

### Authentication

- JWT-based stateless authentication with access and refresh tokens
- OAuth 2.0 and OpenID Connect for third-party identity providers
- SAML 2.0 for enterprise SSO
- Multi-factor authentication (TOTP, SMS)
- Passwordless authentication via magic link
- Brute-force protection with progressive delays and account lockout

### Authorization

- Role-based access control (RBAC) with dynamic role creation
- Attribute-based access control (ABAC) for fine-grained permissions
- Field-level access control on forms and records
- API endpoint authorization with method-level security
- Tenant-scoped permission validation on every request

### Data Protection

- AES-256 encryption at rest for all stored data
- TLS 1.3 for all data in transit
- Field-level encryption for sensitive data (PII, PHI)
- Database-level Row-Level Security as defense in depth
- Automatic PII detection and classification
- Data masking for non-privileged users

### Compliance Readiness

| Standard | Status | Implementation |
|---|---|---|
| **GDPR** | Ready | Data export, erasure, consent management, DPA support |
| **HIPAA** | Ready | Access logging, field encryption, BAA support, audit trail |
| **SOC 2** | Aligned | Access controls, encryption, monitoring, incident response |
| **PCI DSS** | Delegated | Payment processing via certified gateways (Stripe, Razorpay) |
| **CCPA** | Ready | Consumer data rights management |

---

## Scalability

### Horizontal Scaling Strategy

```
Traffic Tier      Architecture                         Tenants
-----------       ----------------------------------   -------
Starter           Single instance, single DB           1 - 50
Growth            Multi-instance, read replicas        50 - 500
Scale             Kubernetes cluster, sharded DB       500 - 5,000
Enterprise        Multi-region, dedicated resources    5,000+
```

### Performance Targets

| Metric | Target |
|---|---|
| API response time (p95) | < 200ms |
| Appointment slot computation | < 500ms |
| Configuration propagation | < 2 seconds |
| Dashboard load time | < 1.5 seconds |
| Search query execution | < 300ms |
| Notification delivery (email) | < 30 seconds |
| System availability | 99.9% |

### Caching Strategy

```
Layer 1: CDN (Static assets, public website pages)
Layer 2: API Gateway (Response caching for read-heavy endpoints)
Layer 3: Application (Redis - Configuration metadata, session data)
Layer 4: Database (PostgreSQL query cache, materialized views for reports)
```

---

## Roadmap

### Phase 1: Foundation (Current)

- [x] Project scaffolding (Spring Boot backend, React frontend)
- [x] Database configuration (PostgreSQL with JPA)
- [x] Authentication framework (Spring Security, JWT)
- [x] API documentation setup (SpringDoc OpenAPI)
- [x] Health monitoring (Spring Actuator)
- [ ] Multi-tenant data isolation (schema-per-tenant with RLS)
- [ ] Configuration Engine (metadata CRUD with JSONB storage)
- [ ] Dynamic Role and Permission management
- [ ] Core API: Services, Customers, Staff CRUD

### Phase 2: Core Engines

- [ ] Dynamic Form Engine (builder + renderer + storage)
- [ ] Appointment Engine (scheduling, availability, booking rules)
- [ ] Workflow Engine (visual designer + execution runtime)
- [ ] Notification Engine (email + SMS + templates)
- [ ] Admin dashboard (tenant configuration UI)

### Phase 3: Business Operations

- [ ] CRM module (customer profiles, interaction history, segments)
- [ ] Billing module (invoicing, payments, tax rules)
- [ ] Dashboard and Reporting (builder + widgets + scheduled reports)
- [ ] Document Management (storage, versioning, templates)
- [ ] Customer Portal (white-labeled, self-service)

### Phase 4: Growth and Intelligence

- [ ] Website Builder (tenant-configurable public pages)
- [ ] Inventory and Resource Management
- [ ] Multi-location support with hierarchy
- [ ] Calendar sync (Google Calendar, Outlook)
- [ ] Payment gateway integrations (Stripe, Razorpay, PayPal)
- [ ] Advanced analytics and business intelligence

### Phase 5: Enterprise and Scale

- [ ] SSO integration (SAML 2.0, OpenID Connect)
- [ ] Multi-region deployment
- [ ] Marketplace for third-party integrations
- [ ] AI-powered scheduling optimization
- [ ] Predictive analytics (no-show prediction, demand forecasting)
- [ ] White-label licensing for vertical SaaS builders
- [ ] Mobile applications (iOS, Android)
- [ ] Offline-first capability for low-connectivity environments

---

## Why EBOP is Different

| Dimension | Traditional SaaS | EBOP |
|---|---|---|
| **Scope** | Single vertical (clinics OR salons OR consulting) | Any service-based business |
| **Customization** | Feature flags and settings pages | Full metadata-driven configuration engine |
| **Workflows** | Pre-built, rigid | Tenant-designed, dynamic |
| **Forms** | Fixed fields | Unlimited custom forms with conditional logic |
| **Scaling** | Re-platform at growth inflection | Architecture scales from 1 to 10,000 locations |
| **Integration** | Point-to-point connectors | API-first with webhook-driven automation |
| **Data Model** | Vendor-controlled | Tenant-extensible with custom fields |
| **Deployment** | Per-customer instances | Multi-tenant with logical isolation |
| **Time to Value** | Weeks to months | Hours to days |

### Competitive Positioning

EBOP occupies a unique position in the market:

- **Not a vertical SaaS**: Unlike Mindbody (fitness), Jane App (healthcare), or Clio (legal), EBOP serves all service verticals from a single platform.
- **Not a no-code builder**: Unlike Bubble or Retool, EBOP is purpose-built for service business operations with pre-built engines for scheduling, billing, CRM, and workflows.
- **Not a generic ERP**: Unlike Odoo or ERPNext, EBOP is optimized for appointment-based and service-based businesses, not manufacturing or supply chain.

EBOP is the configurable operating platform that sits between vertical SaaS (too rigid) and no-code platforms (too generic).

---

## Development Principles

### 1. Schema-Driven Development

Every data structure is defined by a schema. API contracts are generated from OpenAPI specifications. Form validation runs against JSON Schema definitions. Database migrations are versioned and reversible.

### 2. Tenant-First Thinking

Every feature is designed with multi-tenancy as a first-class requirement. No shared mutable state. No cross-tenant data leakage. No configuration interference. Tenant isolation is not an afterthought --- it is the foundation.

### 3. Configuration as Data

Business logic that varies between tenants is stored as data, not code. The codebase contains engines that interpret configuration. Tenant-specific behavior is never hard-coded.

### 4. Event-Driven Architecture

State changes emit events. Workflows subscribe to events. Notifications are triggered by events. Audit logs are written from events. This decouples modules and enables extensibility without modifying core code.

### 5. API-First Design

Every user-facing capability is backed by a versioned API. The frontend is a consumer of the API, not a privileged client. Third-party integrations have the same access as the platform UI.

### 6. Progressive Disclosure

The admin interface does not overwhelm new tenants with enterprise features. Configuration options are revealed progressively as the tenant's usage grows. A sole proprietor sees a simple setup. A multi-location chain sees the full configuration surface.

### 7. Immutable Audit Trail

Every data mutation is recorded with the actor, timestamp, previous value, and new value. Audit records are append-only and cannot be modified or deleted. This is a regulatory requirement for healthcare and legal tenants and a trust requirement for all tenants.

### 8. Fail-Safe Defaults

Every configuration has a sensible default. A tenant that configures nothing still has a functional system. Defaults are designed to be safe, not optimal --- tenants optimize through configuration.

### 9. Observability by Design

Every service emits structured logs, metrics, and traces. Tenant-scoped dashboards allow platform operators to monitor health per tenant. Anomaly detection identifies tenants experiencing degraded performance before they report it.

### 10. Zero-Downtime Operations

Configuration changes, schema migrations, and feature deployments are performed without downtime. Blue-green deployments and feature flags ensure that running tenants are never disrupted.

---

## Getting Started

### Prerequisites

- Java 17+
- Node.js 20+
- PostgreSQL 16+
- Redis 7+ (optional for development)

### Backend Setup

```bash
# Clone the repository
git clone https://github.com/your-org/ebop.git
cd ebop

# Configure environment
cp .env.example .env
# Edit .env with your database credentials

# Start the backend
cd backend
./mvnw spring-boot:run
```

The API server starts at `http://localhost:8080`. API documentation is available at `http://localhost:8080/swagger-ui.html`.

### Frontend Setup

```bash
# Install dependencies
cd bop
npm install

# Start the development server
npm run dev
```

The frontend development server starts at `http://localhost:5173`.

### Environment Variables

| Variable | Description | Default |
|---|---|---|
| `SERVER_PORT` | Backend server port | `8080` |
| `DB_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/ebop` |
| `DB_USERNAME` | Database username | `ebop` |
| `DB_PASSWORD` | Database password | `ebop` |
| `JWT_SECRET` | JWT signing secret (change in production) | `change-me-please` |
| `JWT_EXPIRATION` | Access token expiration (ms) | `3600000` (1 hour) |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiration (ms) | `604800000` (7 days) |

---

## Project Structure

```
ebop/
├── backend/                        # Spring Boot API server
│   ├── src/main/java/
│   │   └── com/example/bop/
│   │       ├── config/             # Security, CORS, tenant config
│   │       ├── core/               # Shared domain: entities, DTOs, exceptions
│   │       ├── tenant/             # Multi-tenant infrastructure
│   │       ├── auth/               # Authentication and authorization
│   │       ├── configuration/      # Dynamic configuration engine
│   │       ├── service/            # Service catalog module
│   │       ├── appointment/        # Appointment engine
│   │       ├── form/               # Dynamic form engine
│   │       ├── workflow/           # Workflow engine
│   │       ├── crm/                # CRM module
│   │       ├── billing/            # Billing and invoicing
│   │       ├── notification/       # Notification engine
│   │       ├── portal/             # Customer portal API
│   │       ├── dashboard/          # Dashboard and reporting
│   │       └── document/           # Document management
│   └── src/main/resources/
│       └── application.yaml        # Application configuration
├── bop/                            # React frontend
│   ├── src/
│   │   ├── components/             # Shared UI components
│   │   ├── pages/                  # Route-level page components
│   │   ├── modules/                # Feature modules (admin, portal, website)
│   │   ├── hooks/                  # Custom React hooks
│   │   ├── services/               # API client layer
│   │   ├── stores/                 # State management
│   │   └── types/                  # TypeScript type definitions
│   └── index.html
├── docs/                           # Architecture and API documentation
├── infra/                          # Docker, Kubernetes, CI/CD
└── .env.example                    # Environment variable template
```

---

## Contributing

EBOP is in active development. Contributions are welcome in the following areas:

1. **Core Engine Development**: Configuration engine, workflow engine, form engine
2. **Module Development**: CRM, billing, notification, dashboard modules
3. **Frontend Components**: Admin UI, customer portal, form builder, workflow designer
4. **Testing**: Unit tests, integration tests, end-to-end tests
5. **Documentation**: API documentation, architecture guides, deployment guides

Please read the contributing guidelines before submitting a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

<p align="center">
  <strong>EBOP</strong> --- The operating platform for service-based businesses.
</p>
