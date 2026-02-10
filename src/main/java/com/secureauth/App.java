package com.secureauth;

import com.secureauth.models.*;
import com.secureauth.policies.NudgePolicy;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        System.out.println("=== SecureAuth Simulator Starting ===\n");
    
        // 1. Create an Enterprise organization
        Organization enterpriseOrg = new Organization(OrganizationType.ENTERPRISE, "org-enterprise");
        System.out.println("Created organization: " + enterpriseOrg.getOrgId() + " (Type: " + enterpriseOrg.getType() + ")");
        
        // 2. Create a developer using OTP (eligible for nudging)
        User alice = new User("user-alice", "org-enterprise", AuthMethod.OTP, Position.DEVELOPER);
        System.out.println("\nCreated user: " + alice.getUserId());
        System.out.println("  - Position: " + alice.getPosition());
        System.out.println("  - Auth Method: " + alice.getAuthMethod());
        System.out.println("  - Has Adopted: " + alice.hasAdopted());
        
        // 3. Get the organization's policy
        NudgePolicy policy = enterpriseOrg.getNudgePolicy();
        System.out.println("\n--- Testing Policy ---");
        
        // 4. Check if alice is eligible
        boolean eligible = policy.isUserEligible(alice);
        System.out.println("Is alice eligible for nudging? " + eligible);
        
        if (eligible) {
            // 5. Get nudge frequency
            int frequency = policy.getNudgeFrequency(alice);
            System.out.println("Nudge frequency for alice: Every " + frequency + " days");
            
            // 6. Customize message
            String baseMessage = "Switch to Passkey for better security!";
            String customMessage = policy.customizeMessage(baseMessage);
            System.out.println("Customized message: " + customMessage);
            
            // 7. Create and send nudge
            NudgeEvent nudge = new NudgeEvent(
                LocalDateTime.now(),
                customMessage,
                NudgeType.EMAIL
            );
            alice.addNudge(nudge);
            System.out.println("✓ Nudge sent to alice!");
            System.out.println("Alice's nudge history size: " + alice.getNudgeHistory().size());
        }
        
        // 8. Test adoption
        System.out.println("\n--- Testing Adoption ---");
        System.out.println("Before adoption:");
        System.out.println("  - Has Adopted: " + alice.hasAdopted());
        System.out.println("  - Auth Method: " + alice.getAuthMethod());
        
        alice.adopt();  // Alice switches to Passkey!
        
        System.out.println("After adoption:");
        System.out.println("  - Has Adopted: " + alice.hasAdopted());
        System.out.println("  - Auth Method: " + alice.getAuthMethod());
        System.out.println("  - Adopted At: " + alice.getAdoptedAt());
        
        // 9. Check eligibility again (should be false now)
        boolean eligibleAfter = policy.isUserEligible(alice);
        System.out.println("  - Still eligible for nudging? " + eligibleAfter + " (should be false!)");
        
        System.out.println("\n=== Test Complete! ===");
    }
} 