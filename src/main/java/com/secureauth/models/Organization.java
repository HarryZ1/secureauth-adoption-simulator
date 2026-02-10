package com.secureauth.models;

import com.secureauth.policies.*;

public class Organization {
    private OrganizationType type;
    private String orgId;
    private NudgePolicy nudgePolicy;

    public Organization(OrganizationType type, String orgId) {
        this.type = type;
        this.orgId = orgId;
        this.nudgePolicy = createNudgePolicy(type);
    }

    private NudgePolicy createNudgePolicy(OrganizationType type) {
        switch (type) {
            case IT:
                return new ITNudgePolicy();
            case CLOUD:
                return new CloudNudgePolicy();
            case ENTERPRISE:
                return new EnterpriseNudgePolicy();
            default:
                return new ITNudgePolicy();
        }
    }

    public OrganizationType getType() {
        return type;
    }

    public String getOrgId() {
        return orgId;
    }

    public NudgePolicy getNudgePolicy() {
        return nudgePolicy;
    }
}
