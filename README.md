# SecureAuth Adoption Simulator

A Java-based simulator designed to model and analyze authentication method adoption patterns in enterprise organizations. This project demonstrates system design principles, behavioral patterns, and clean architecture practices.

## Project Purpose

Built as a learning project to prepare for software engineering internships, focusing on:
- **System Design**: Domain-driven design with clear entity/value object separation
- **Design Patterns**: Strategy pattern for policy-based behavior
- **State Management**: Tracking user authentication transitions and nudge history
- **Clean Architecture**: Separation of concerns between models, policies, and business logic

## Real-World Use Case

Organizations want to migrate users from less secure authentication methods (OTP, passwords) to modern Passkey authentication. This simulator models:
- **Targeted Nudging**: Send reminders to users still using legacy auth methods
- **Position-Based Policies**: Different nudge frequencies for executives vs. developers
- **Organization-Specific Rules**: IT, Cloud, and Enterprise orgs have different policies
- **Adoption Tracking**: Monitor when users switch to Passkey authentication

## Architecture

### Domain Models (`src/com/secureauth/models/`)

**Entities** (have identity):
- **User** - Represents a user with authentication method, position, and nudge history
- **Organization** - Represents an organization with a specific nudge policy

**Value Objects** (immutable data):
- **NudgeEvent** - A single nudge with timestamp, message, and type

**Enums** (type-safe constants):
- `AuthMethod`: PASSWORD, OTP, PASSKEY
- `Position`: DEVELOPER, MANAGER, EXECUTIVE
- `NudgeType`: EMAIL, BANNER, POPUP
- `OrganizationType`: IT, CLOUD, ENTERPRISE

### Strategy Pattern (`src/com/secureauth/policies/`)

The `NudgePolicy` interface defines three behaviors:
```java
int getNudgeFrequency(User user)      // How often to nudge (in days)
String customizeMessage(String msg)    // Customize message per org type
boolean isUserEligible(User user)      // Who can be nudged
```

**Concrete Implementations**:
- **EnterpriseNudgePolicy** - Developers nudged every 3 days, managers every 7 days
- **CloudNudgePolicy** - Cloud-specific messaging and rules
- **ITNudgePolicy** - IT-specific messaging and rules

### Key Design Decisions

1. **Rich Domain Model**: Business logic (`adopt()`, `addNudge()`) lives in the `User` class rather than scattered across services
2. **Dependency Inversion**: `Organization` depends on the `NudgePolicy` interface, not concrete implementations
3. **Single Responsibility**: Each policy class handles one organization type's rules
4. **Composition Over Inheritance**: Policies are composed into organizations rather than using inheritance

## How to Run

### From Terminal (Command Line)

**Step 1**: Navigate to project root
```bash
cd ~/Documents/google-test-project
```

**Step 2**: Compile and Run (one-liner)
```bash
mkdir -p bin && javac -d bin $(find src -name "*.java") && java -cp bin com.secureauth.App
```

### Expected Output
```
=== SecureAuth Simulator Starting ===

Created organization: org-enterprise (Type: ENTERPRISE)

Created user: user-alice
  - Position: DEVELOPER
  - Auth Method: OTP
  - Has Adopted: false

--- Testing Policy ---
Is alice eligible for nudging? true
Nudge frequency for alice: Every 3 days
Customized message: This is for Enterprise: Switch to Passkey for better security! Goodbye from Enterprise.
✓ Nudge sent to alice!
Alice's nudge history size: 1

--- Testing Adoption ---
Before adoption:
  - Has Adopted: false
  - Auth Method: OTP
After adoption:
  - Has Adopted: true
  - Auth Method: PASSKEY
  - Adopted At: 2026-02-07T10:30:45.123
  - Still eligible for nudging? false (should be false!)

=== Test Complete! ===
```

## Project Structure

```
google-test-project/
├── src/
│   └── com/
│       └── secureauth/
│           ├── App.java                      # Main entry point with test scenarios
│           ├── models/                        # Domain models and enums
│           │   ├── User.java
│           │   ├── Organization.java
│           │   ├── NudgeEvent.java
│           │   ├── AuthMethod.java
│           │   ├── Position.java
│           │   ├── NudgeType.java
│           │   └── OrganizationType.java
│           └── policies/                      # Strategy pattern implementations
│               ├── NudgePolicy.java           # Interface
│               ├── EnterpriseNudgePolicy.java
│               ├── CloudNudgePolicy.java
│               └── ITNudgePolicy.java
└── bin/                                       # Compiled .class files (gitignored)
```

## Future Enhancements

- [ ] Add JUnit tests with 80%+ coverage
- [ ] Implement `NudgeService` for orchestration logic
- [ ] Add Repository pattern for data persistence
- [ ] Differentiate policy implementations (currently similar)
- [ ] Add metrics tracking (adoption rate, nudge effectiveness)
- [ ] Create configurable policy rules (load from config file)
- [ ] Add more sophisticated eligibility rules (time-based, nudge fatigue)

## Learning Outcomes

Through this project, I learned:
- How to apply the **Strategy Pattern** to handle varying behavior across organization types
- The difference between **entities** (identity matters) and **value objects** (data only)
- **Rich domain models** vs. anemic domain models + services
- When to use **enums** for type safety instead of strings
- **Polymorphism** through interfaces (programming to `NudgePolicy`, not concrete classes)
- Proper **separation of concerns** between models, policies, and business logic

## Built With

- Java (core language features)
- No external dependencies (pure Java project)

---

*Built as a learning project to prepare for software engineering internships.*