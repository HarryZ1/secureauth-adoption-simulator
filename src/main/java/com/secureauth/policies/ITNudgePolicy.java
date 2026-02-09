package com.secureauth.policies;

import com.secureauth.models.User;
import com.secureauth.models.AuthMethod;
import com.secureauth.models.Position;

public class ITNudgePolicy implements NudgePolicy {
    // Retrieves the cooldown of nudges for each position in days
    public int getNudgeFrequency(User user) {
        Position position = user.getPosition();
        if (position == Position.EXECUTIVE) {
            return 0;
        }

        switch (position) {
            case DEVELOPER:
                return 3;
            case MANAGER:
                return 7;
            default:
                return 3;
        }
    }

    public String customizeMessage(String message) {
        return "This is for IT: " + message + " Goodbye from IT.";
    }

    public boolean isUserEligible(User user) {
        return !user.hasAdopted() && user.getAuthMethod() != AuthMethod.PASSKEY &&
                user.getPosition() != Position.EXECUTIVE;
    }
}
