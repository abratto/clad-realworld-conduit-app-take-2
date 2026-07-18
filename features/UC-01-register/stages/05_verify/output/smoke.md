# Smoke test — UC-01-register

Executed via `CucumberTest` which runs the embedded Micronaut server and exercises all register-account scenarios.

## Commands

```bash
mvn -f app/backend/pom.xml test -Dtest="CucumberTest" -pl .
```

## Results

11 Cucumber scenarios: 4 login + 7 register-account → **0 failures, 0 errors**

### Smoke: successful registration

```bash
# Using Cucumber scenario "Register a new user successfully"
# POST /api/users with username "jdoe", email "jdoe@test.com", password "secret123"
# → HTTP 201
# → Response body contains: username "jdoe", email "jdoe@test.com", token, null
```

### Smoke: validation errors

```bash
# Each produces HTTP 422 with body containing "can't be blank":
# - blank username: POST /api/users with username ""
# - blank email:    POST /api/users with email ""
# - blank password: POST /api/users with password ""
```

### Smoke: conflict errors

```bash
# Each produces HTTP 409 with body containing "has already been taken":
# - duplicate username: POST /api/users with existing username
# - duplicate email:    POST /api/users with existing email
```

## Full test suite

```bash
mvn test -Dtest="!ConcurrencyTest"
# → 66 tests, 0 failures, 0 errors ✅
```
