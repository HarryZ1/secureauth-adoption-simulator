package com.secureauth.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.secureauth.models.AuthMethod;
import com.secureauth.models.NudgeType;
import com.secureauth.models.Organization;
import com.secureauth.models.OrganizationType;
import com.secureauth.models.Position;
import com.secureauth.models.User;

public class NudgeServiceTest {
    @Test
    void addUser_throwsWhenUserAlreadyExists() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        NudgeService nudgeService = new NudgeService();

        nudgeService.addUser(user);

        assertThrows(IllegalArgumentException.class, () -> {
            nudgeService.addUser(user);
        });
    }

    @Test
    void addUser_addsUserToUsersCollection() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        NudgeService nudgeService = new NudgeService();

        nudgeService.addUser(user);

        assertEquals(1, nudgeService.getUsersById().size());
        assertEquals(user, nudgeService.getUsersById().get(user.getUserId()));
    }

    @Test
    void addOrganization_throwsWhenOrgAlreadyExists() {
        Organization org = new Organization(OrganizationType.CLOUD, "org-1");
        NudgeService nudgeService = new NudgeService();

        nudgeService.addOrganization(org);

        assertThrows(IllegalArgumentException.class, () -> {
            nudgeService.addOrganization(org);
        });
    }

    @Test
    void addOrganization_addsOrgToOrganizationsCollection() {
        Organization org = new Organization(OrganizationType.CLOUD, "org-1");
        NudgeService nudgeService = new NudgeService();

        nudgeService.addOrganization(org);

        assertEquals(1, nudgeService.getOrganizationsById().size());
        assertEquals(org, nudgeService.getOrganizationsById().get(org.getOrgId()));
    }

    @Test
    void nudgeUser_returnsFalseWhenUserNotEligible() {
        NudgeService nudgeService = new NudgeService();
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.EXECUTIVE);
        Organization org = new Organization(OrganizationType.CLOUD, user.getOrgId());

        nudgeService.addUser(user);
        nudgeService.addOrganization(org);

        assertFalse(nudgeService.nudgeUser(user.getUserId(), "Hello", NudgeType.BANNER));
        assertTrue(user.getNudgeHistory().isEmpty());
    }

    @Test
    void nudgeUser_returnsFalseWhenUserCooldownNotOver() {
        NudgeService nudgeService = new NudgeService();
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        Organization org = new Organization(OrganizationType.CLOUD, user.getOrgId());

        nudgeService.addUser(user);
        nudgeService.addOrganization(org);
        nudgeService.nudgeUser(user.getUserId(), "Hello1", NudgeType.BANNER);

        assertFalse(nudgeService.nudgeUser(user.getUserId(), "Hello2", NudgeType.BANNER));
        assertEquals(1, user.getNudgeHistory().size());
    }

    @Test
    void nudgeUser_returnsTrueWhenUserEligibleAndCooldownOver() {
        NudgeService nudgeService = new NudgeService();
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        Organization org = new Organization(OrganizationType.CLOUD, user.getOrgId());

        nudgeService.addUser(user);
        nudgeService.addOrganization(org);

        assertTrue(nudgeService.nudgeUser(user.getUserId(), "Hello", NudgeType.EMAIL));
        assertEquals(1, user.getNudgeHistory().size());
    }
}
