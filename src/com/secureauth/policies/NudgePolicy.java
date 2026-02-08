package com.secureauth.policies;

import com.secureauth.models.User;

public interface NudgePolicy {
    int getNudgeFrequency(User user);
    String customizeMessage(String message);
    boolean isUserEligible(User user);
}
