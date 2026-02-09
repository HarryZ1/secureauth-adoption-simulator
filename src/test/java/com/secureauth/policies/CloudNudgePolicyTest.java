package com.secureauth.policies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.secureauth.models.AuthMethod;
import com.secureauth.models.Position;
import com.secureauth.models.User;

public class CloudNudgePolicyTest {
    @Test
    void getNudgeFrequency_executive() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.EXECUTIVE);
        NudgePolicy policy = new CloudNudgePolicy();

        assertEquals(0, policy.getNudgeFrequency(user));
    }

    @Test
    void getNudgeFrequency_developer() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        NudgePolicy policy = new CloudNudgePolicy();

        assertEquals(3, policy.getNudgeFrequency(user));
    }

    @Test
    void getNudgeFrequency_manager() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.MANAGER);
        NudgePolicy policy = new CloudNudgePolicy();

        assertEquals(7, policy.getNudgeFrequency(user));
    }

    @Test
    void customizeMessage_setCorrectMessageCustomizations() {
        NudgePolicy policy = new CloudNudgePolicy();

        assertEquals("This is for Cloud: Hello! Goodbye from Cloud.", policy.customizeMessage("Hello!"));
    }

    @Test
    void isUserEligible_returnsTrue() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.MANAGER);
        NudgePolicy policy = new CloudNudgePolicy();

        assertTrue(policy.isUserEligible(user));
    }

    @Test
    void isUserEligible_returnsFalseWhenAdopted() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.MANAGER);
        NudgePolicy policy = new CloudNudgePolicy();
        user.adopt();

        assertFalse(policy.isUserEligible(user));
    }

    @Test
    void isUserEligible_returnsFalseWhenPasskey() {
        User user = new User("user-1", "org-1", AuthMethod.PASSKEY, Position.MANAGER);
        NudgePolicy policy = new CloudNudgePolicy();
        
        assertFalse(policy.isUserEligible(user));
    }

    @Test
    void isUserEligible_returnsFalseWhenExecutive() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.EXECUTIVE);
        NudgePolicy policy = new CloudNudgePolicy();

        assertFalse(policy.isUserEligible(user));
    }
}
