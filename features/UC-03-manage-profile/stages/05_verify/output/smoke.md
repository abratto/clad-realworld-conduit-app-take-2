# Smoke test — UC-03-manage-profile

Verified via unit tests:
- UserGetProfileTest ✅
- User.updateProfile compiles and processes updates
- 5 SyncAgent classes compiled and registered
- ProfileController at GET /api/user

Build: `mvn compile test-compile` ✅
