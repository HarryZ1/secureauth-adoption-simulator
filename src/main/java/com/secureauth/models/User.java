package com.secureauth.models;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private String userId;
    private String orgId;
    private AuthMethod authMethod;
    private Position position;
    private boolean hasAdopted;
    private LocalDateTime adoptedAt;
    private List<NudgeEvent> nudgeHistory;

    public User(String userId, String orgId, AuthMethod authMethod, 
                Position position) {
        this.userId = userId;
        this.orgId = orgId;
        this.authMethod = authMethod;
        this.position = position;
        this.hasAdopted = authMethod == AuthMethod.PASSKEY;
        this.adoptedAt = null;
        this.nudgeHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public AuthMethod getAuthMethod() {
        return authMethod;
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasAdopted() {
        return hasAdopted;
    }

    public LocalDateTime getAdoptedAt() {
        return adoptedAt;
    }

    public List<NudgeEvent> getNudgeHistory() {
        return nudgeHistory;
    }

    public void addNudge(NudgeEvent nudgeEvent) {
        nudgeHistory.add(nudgeEvent);
    }

    public void adopt() {
        if (hasAdopted) {
            throw new IllegalStateException();
        }
        
        hasAdopted = true;
        adoptedAt = LocalDateTime.now();
        authMethod = AuthMethod.PASSKEY;
    }

}
