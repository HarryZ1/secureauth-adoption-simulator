package com.secureauth.policies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.secureauth.models.AuthMethod;
import com.secureauth.models.Position;
import com.secureauth.models.User;

public class EnterpriseNudgePolicyTest {
    @Test
    void getNudgeFrequency_returnsZeroWhenExecutive() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.EXECUTIVE);
        NudgePolicy policy = new EnterpriseNudgePolicy();

        assertEquals(0, policy.getNudgeFrequency(user));
    }

    @Test
    void getNudgeFrequency_returnsThreeWhenDeveloper() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        NudgePolicy policy = new EnterpriseNudgePolicy();

        assertEquals(3, policy.getNudgeFrequency(user));
    }

    @Test
    void getNudgeFrequency_returnsSevenWhenManager() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.MANAGER);
        NudgePolicy policy = new EnterpriseNudgePolicy();

        assertEquals(7, policy.getNudgeFrequency(user));
    }

    @Test
    void customizeMessage_wrapsMessageWithOrgText() {
        NudgePolicy policy = new EnterpriseNudgePolicy();

        assertEquals("This is for Enterprise: Hello! Goodbye from Enterprise.", policy.customizeMessage("Hello!"));
    }

    @Test
    void isUserEligible_returnsTrueWhenEligible() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.MANAGER);
        NudgePolicy policy = new EnterpriseNudgePolicy();

        assertTrue(policy.isUserEligible(user));
    }

    @Test
    void isUserEligible_returnsFalseWhenAdopted() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.MANAGER);
        NudgePolicy policy = new EnterpriseNudgePolicy();
        user.adopt();

        assertFalse(policy.isUserEligible(user));
    }

    @Test
    void isUserEligible_returnsFalseWhenPasskey() {
        User user = new User("user-1", "org-1", AuthMethod.PASSKEY, Position.MANAGER);
        NudgePolicy policy = new EnterpriseNudgePolicy();
        
        assertFalse(policy.isUserEligible(user));
    }

    @Test
    void isUserEligible_returnsFalseWhenExecutive() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.EXECUTIVE);
        NudgePolicy policy = new EnterpriseNudgePolicy();

        assertFalse(policy.isUserEligible(user));
    }
}
