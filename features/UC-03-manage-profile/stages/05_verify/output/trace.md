Resume point: next feature is UC-04-view-profile

# Verification trace — UC-03-manage-profile

## Scenario: view-profile

**Gherkin scenario:** View own profile (manage-profile.feature)
**Use-case:** view-profile (usecase.md)

### Token chain (expected)
```
Web/request[GET /api/user] → Web.handle → Session.lookup → 
User.getProfile → Session.grant → Web.respond[200]
```

### Token chain (actual)
Verified via unit tests (UserGetProfileTest, sync agent tests).
End-to-end verified via ProfileController with embedded server.

## Scenario: update-profile

**Use-case:** update-profile (usecase.md)
Covers bio/image/email/username/password updates with validation.
