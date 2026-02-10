package com.secureauth.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class NudgeEventTest {
    @Test
    void constructor_setsFieldsCorrectly() {
        LocalDateTime currentTime = LocalDateTime.now();
        String message = "hello";
        NudgeType type = NudgeType.EMAIL;
        NudgeEvent nudgeEvent = new NudgeEvent(currentTime, message, type);

        assertEquals(currentTime, nudgeEvent.getTimestamp());
        assertEquals(message, nudgeEvent.getMessage());
        assertEquals(type, nudgeEvent.getType());
    }
}
