package com.secureauth.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.secureauth.models.NudgeEvent;
import com.secureauth.models.NudgeType;
import com.secureauth.models.Organization;
import com.secureauth.models.User;
import com.secureauth.policies.NudgePolicy;

public class NudgeService {
    private Map<String, User> usersById;
    private Map<String, Organization> organizationsById;
    
    public NudgeService() {
        this.usersById = new HashMap<>();
        this.organizationsById = new HashMap<>();
    }

    public Map<String, User> getUsersById() {
        return usersById;
    }

    public Map<String, Organization> getOrganizationsById() {
        return organizationsById;
    }

    public void addUser(User user) {
        if (usersById.containsKey(user.getUserId())) {
            throw new IllegalArgumentException();
        }

        usersById.put(user.getUserId(), user);
    }

    public void addOrganization(Organization organization) {
        if (organizationsById.containsKey(organization.getOrgId())) {
            throw new IllegalArgumentException();
        }

        organizationsById.put(organization.getOrgId(), organization);
    }

    public boolean nudgeUser(String userId, String message, NudgeType nudgeType) {
        User user = usersById.get(userId);
        Organization org = organizationsById.get(user.getOrgId());
        NudgePolicy policy = org.getNudgePolicy();

        if (policy.isUserEligible(user) && isCooldownOver(user, policy)) {
            NudgeEvent newNudgeEvent = new NudgeEvent(LocalDateTime.now(), policy.customizeMessage(message), nudgeType);
            user.addNudge(newNudgeEvent);
            return true;
        }

        return false;
    }

    private boolean isCooldownOver(User user, NudgePolicy policy) {
        List<NudgeEvent> nudgeHistory = user.getNudgeHistory();
        NudgeEvent mostRecentNudgeEvent = !nudgeHistory.isEmpty() ? nudgeHistory.getLast() : null;
        int cooldown = policy.getNudgeFrequency(user);

        return mostRecentNudgeEvent == null || ChronoUnit.DAYS.between(mostRecentNudgeEvent.getTimestamp(), LocalDateTime.now()) >= cooldown;
    }


}
