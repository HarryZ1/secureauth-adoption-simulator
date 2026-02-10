package com.secureauth.models;

import java.time.LocalDateTime;

public class NudgeEvent {
    private LocalDateTime timestamp;
    private String message;
    private NudgeType type;

    public NudgeEvent(LocalDateTime timestamp, String message, NudgeType type) {
        this.timestamp = timestamp;
        this.message = message;
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public NudgeType getType() {
        return type;
    }

}
