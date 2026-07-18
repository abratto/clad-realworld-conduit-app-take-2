# User — conceptual data model

Step 1: Familiar: User has username "ada", email "ada@test.com", bio "...", image null.
Facts: User has username, email, passwordHash, bio (opt), image (opt).

Step 2: Entity: User (UserId). Facts: 5 fact types (1:1 each).
Population: User(u1) has username "ada".

Step 3: No combinations or derivations.

Step 4: username unique, email unique.

Step 5: username, email, passwordHash mandatory. bio, image optional.

Step 6-7: No additional constraints.
