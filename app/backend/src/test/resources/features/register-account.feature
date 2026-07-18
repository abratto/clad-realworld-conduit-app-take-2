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
    And the response body contains "jdoe"
    And the response body contains "jdoe@test.com"
    And the response body contains "token"
    And the response body contains "null"

  @register-account @failure-path @no-state-change
  Scenario: Register with blank username
    Given no user exists with username ""
    When the user submits POST /api/users with username "", email "jdoe@test.com", and password "secret123"
    Then the response status is 422
    And the response body contains "can't be blank"

  @register-account @failure-path @no-state-change
  Scenario: Register with blank email
    Given no user exists with email ""
    When the user submits POST /api/users with username "jdoe", email "", and password "secret123"
    Then the response status is 422
    And the response body contains "can't be blank"

  @register-account @failure-path @no-state-change
  Scenario: Register with blank password
    Given no user exists with username "jdoe"
    When the user submits POST /api/users with username "jdoe", email "jdoe@test.com", and password ""
    Then the response status is 422
    And the response body contains "can't be blank"

  @register-account @failure-path @no-state-change
  Scenario: Register with duplicate username
    Given a user exists with username "existing_user"
    When the user submits POST /api/users with username "existing_user", email "new@test.com", and password "secret123"
    Then the response status is 409
    And the response body contains "has already been taken"

  @register-account @failure-path @no-state-change
  Scenario: Register with duplicate email
    Given a user exists with email "existing@test.com"
    When the user submits POST /api/users with username "new_user", email "existing@test.com", and password "secret123"
    Then the response status is 409
    And the response body contains "has already been taken"

  @contract @post-api-users
  Scenario: POST /api/users matches the external contract
    Given the system is running
    When the user submits POST /api/users with username "contract_test", email "contract@test.com", and password "secret123"
    Then the response status is 201
    And the response body contains "username"
    And the response body contains "token"
    And the response body contains "email"
