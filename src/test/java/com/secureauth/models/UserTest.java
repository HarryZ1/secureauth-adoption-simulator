package com.secureauth.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class UserTest {
    @Test
    void constructor_setsFieldsCorrectly() {
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
    void constructor_withPasskey_setsHasAdoptedTrue() {
        User user = new User("user-1", "org-1", AuthMethod.PASSKEY, Position.DEVELOPER);

        assertTrue(user.hasAdopted());
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

    @Test
    void addNudge_addsToHistory() {
        User user = new User("user-1", "org-1", AuthMethod.OTP, Position.DEVELOPER);
        LocalDateTime time = LocalDateTime.now();
        NudgeEvent nudge = new NudgeEvent(time, "hello", NudgeType.EMAIL);

        user.addNudge(nudge);

        assertEquals(1, user.getNudgeHistory().size());
        assertEquals(nudge, user.getNudgeHistory().get(0));
    }

    @Test
    void addNudge_throwsWhenAlreadyAdopted() {
        User user = new User("user-1", "org-1", AuthMethod.PASSKEY, Position.DEVELOPER);
        LocalDateTime time = LocalDateTime.now();
        NudgeEvent nudge = new NudgeEvent(time, "hello", NudgeType.EMAIL);

        assertThrows(IllegalStateException.class, () -> {
            user.addNudge(nudge);
        });
    }
}
