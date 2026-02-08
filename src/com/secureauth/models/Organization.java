package com.secureauth.models;

import com.secureauth.policies.*;

public class Organization {
    private OrganizationType type;
    private String orgID;
    private NudgePolicy nudgePolicy;

    public Organization(OrganizationType type, String orgID) {
        this.type = type;
        this.orgID = orgID;
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

    public String getOrgID() {
        return orgID;
    }

    public NudgePolicy getNudgePolicy() {
        return nudgePolicy;
    }
}
