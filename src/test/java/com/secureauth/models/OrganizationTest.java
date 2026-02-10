package com.secureauth.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.secureauth.policies.CloudNudgePolicy;
import com.secureauth.policies.EnterpriseNudgePolicy;
import com.secureauth.policies.ITNudgePolicy;

public class OrganizationTest {
    @Test
    void constructor_setsFieldsCorrectly() {
        OrganizationType type = OrganizationType.CLOUD;
        String orgId = "org-1";
        Organization organization = new Organization(type, orgId);
        
        assertEquals(type, organization.getType());
        assertEquals(orgId, organization.getOrgId());
    }

    @Test
    void constructor_withCloud_createsCloudPolicy() {
        Organization organization = new Organization(OrganizationType.CLOUD, "org-1");

        assertTrue(organization.getNudgePolicy() instanceof CloudNudgePolicy);
    }

    @Test
    void constructor_withEnterprise_createsEnterprisePolicy() {
        Organization organization = new Organization(OrganizationType.ENTERPRISE, "org-1");

        assertTrue(organization.getNudgePolicy() instanceof EnterpriseNudgePolicy);
    }

    @Test
    void constructor_withIT_createsITPolicy() {
        Organization organization = new Organization(OrganizationType.IT, "org-1");

        assertTrue(organization.getNudgePolicy() instanceof ITNudgePolicy);
    }
}
