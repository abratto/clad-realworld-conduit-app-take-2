@UC-01 @register-account
Feature: Register Account

  As a Reader
  I want to create a new account with email, username, and password
  So that I can authenticate and perform Member actions

  Background:
    Given the system is running

  @register-account @happy-path
  Scenario: Register a new user successfully
    Given no user exists with username "jdoe"
    Given no user exists with email "jdoe@test.com"
    When the user submits POST /api/users with username "jdoe", email "jdoe@test.com", and password "secret123"
    Then the response status is 201
    And the response body has JSON path "$.user.username" with type String and value "jdoe"
    And the response body has JSON path "$.user.email" with type String and value "jdoe@test.com"
    And the response body has JSON path "$.user.bio" with value null
    And the response body has JSON path "$.user.image" with value null
    And the response body has JSON path "$.user.token" with type String and isNotEmpty
    And the runtime token chain matches:
      """
      Web/request[POST /api/users] -> Web.handle -> Routed
      Web.handle[Routed] -> User.register -> Registered
      User.register[Registered] -> Session.grant -> Granted
      Session.grant[Granted] -> Web.respond -> 201
      """

  @register-account @failure-path @no-state-change
  Scenario: Register with blank username
    Given no user exists with username ""
    When the user submits POST /api/users with username "", email "jdoe@test.com", and password "secret123"
    Then the response status is 422
    And the response body matches {"errors":{"username":["can't be blank"]}}
    And no state is modified in any concept
    And the runtime token chain matches:
      """
      Web/request[POST /api/users] -> Web.handle -> Refused:blankFields
      Web.handle[Refused:blankFields] -> Web.respond -> 422
      """

  @register-account @failure-path @no-state-change
  Scenario: Register with blank email
    Given no user exists with email ""
    When the user submits POST /api/users with username "jdoe", email "", and password "secret123"
    Then the response status is 422
    And the response body matches {"errors":{"email":["can't be blank"]}}
    And no state is modified in any concept
    And the runtime token chain matches:
      """
      Web/request[POST /api/users] -> Web.handle -> Refused:blankFields
      Web.handle[Refused:blankFields] -> Web.respond -> 422
      """

  @register-account @failure-path @no-state-change
  Scenario: Register with blank password
    Given no user exists with username "jdoe"
    When the user submits POST /api/users with username "jdoe", email "jdoe@test.com", and password ""
    Then the response status is 422
    And the response body matches {"errors":{"password":["can't be blank"]}}
    And no state is modified in any concept
    And the runtime token chain matches:
      """
      Web/request[POST /api/users] -> Web.handle -> Refused:blankFields
      Web.handle[Refused:blankFields] -> Web.respond -> 422
      """

  @register-account @failure-path @no-state-change
  Scenario: Register with duplicate username
    Given a user exists with username "existing_user"
    When the user submits POST /api/users with username "existing_user", email "new@test.com", and password "secret123"
    Then the response status is 409
    And the response body matches {"errors":{"username":["has already been taken"]}}
    And no state is modified in any concept
    And the runtime token chain matches:
      """
      Web/request[POST /api/users] -> Web.handle -> Routed
      Web.handle[Routed] -> User.register -> refused
      User.register[refused] -> Web.respond -> 409
      """

  @register-account @failure-path @no-state-change
  Scenario: Register with duplicate email
    Given a user exists with email "existing@test.com"
    When the user submits POST /api/users with username "new_user", email "existing@test.com", and password "secret123"
    Then the response status is 409
    And the response body matches {"errors":{"email":["has already been taken"]}}
    And no state is modified in any concept
    And the runtime token chain matches:
      """
      Web/request[POST /api/users] -> Web.handle -> Routed
      Web.handle[Routed] -> User.register -> refused
      User.register[refused] -> Web.respond -> 409
      """

  @contract @post-api-users
  Scenario: POST /api/users matches the external contract
    Given the system is running
    When the user submits POST /api/users with username "contract_test", email "contract@test.com", and password "secret123"
    Then the response status is 201
    And the response body has JSON path "$.user.username" with type String
    And the response body has JSON path "$.user.email" with type String
    And the response body has JSON path "$.user.bio" with type nullable String
    And the response body has JSON path "$.user.image" with type nullable String
    And the response body has JSON path "$.user.token" with type String
    And the primary error response body matches error envelope {"errors": {"<field>": ["<message>"]}}
