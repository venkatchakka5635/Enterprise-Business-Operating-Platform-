package com.example.bop.common.tenant;

import java.util.UUID;

public final class TenantContext {

    private static final ThreadLocal<UUID> TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<UUID> USER_ID = new ThreadLocal<>();

    private TenantContext() {}

    public static UUID getTenantId() {
        return TENANT_ID.get();
    }

    public static void setTenantId(UUID tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static UUID getUserId() {
        return USER_ID.get();
    }

    public static void setUserId(UUID userId) {
        USER_ID.set(userId);
    }

    public static void clear() {
        TENANT_ID.remove();
        USER_ID.remove();
    }
}
