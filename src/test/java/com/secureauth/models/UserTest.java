package com.secureauth.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserTest {
    @Test
    void constructor_setsCoorectDefaults() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);

        assertEquals("user-1", user.getUserId());
        assertEquals("org-1", user.getOrgId());
        assertEquals(AuthMethod.OTP, user.getAuthMethod());
        assertEquals(Position.DEVELOPER, user.getPosition());
        assertFalse(user.hasAdopted());
        assertNull(user.getAdoptedAt());
        assertTrue(user.getNudgeHistory().isEmpty());
    }

    @Test
    void adopt_throwsWhenAlreadyAdopted() {
        User user = new User("user-1", "org-1", AuthMethod.PASSKEY, Position.DEVELOPER);

        assertThrows(IllegalStateException.class, () -> {
            user.adopt();
        });
    }

    @Test
    void adopt_switchesToPasskey() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);

        user.adopt();

        assertTrue(user.hasAdopted());
        assertNotNull(user.getAdoptedAt());
        assertEquals(AuthMethod.PASSKEY, user.getAuthMethod());
    }
}
