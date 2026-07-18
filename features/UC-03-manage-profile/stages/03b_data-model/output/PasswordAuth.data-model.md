# PasswordAuth — conceptual data model

Step 1: PasswordAuth for ada has hash "...", 0 failed attempts.
Facts: PasswordAuth has passwordHash, failedAttempts, lockedUntil (opt).

Step 2: Entity: PasswordAuth (UserId). Facts: 3 fact types.

Step 3-7: failedAttempts is non-negative integer.
