@UC-02 @sign-in
Feature: Sign In

  As a Member
  I want to authenticate with email and password
  So that I obtain a JWT for subsequent requests

  Background:
    Given the system is running
    Given a registered user exists

  @sign-in @happy-path
  Scenario: Sign in with valid email and password
    Given a user is registered with email "signin@test.com" and password "mypass"
    When the user submits login with email "signin@test.com" and password "mypass"
    Then the response status is 200
    And the response body contains "token"
    And the response body contains "signin@test.com"

  @sign-in @failure-path @no-state-change
  Scenario: Sign in with unknown email
    When the user submits login with email "unknown@test.com" and password "anything"
    Then the response status is 401
    And the response body contains "invalid"

  @sign-in @failure-path @no-state-change
  Scenario: Sign in with wrong password
    Given a user is registered with email "wpass@test.com" and password "correct"
    When the user submits login with email "wpass@test.com" and password "wrong"
    Then the response status is 401
    And the response body contains "invalid"

  @sign-in @failure-path @no-state-change
  Scenario: Sign in with blank email
    When the user submits login with email "" and password "anything"
    Then the response status is 422
    And the response body contains "can't be blank"

  @sign-in @failure-path @no-state-change
  Scenario: Sign in with blank password
    When the user submits login with email "blank@test.com" and password ""
    Then the response status is 422
    And the response body contains "can't be blank"

  @contract @post-api-users-login
  Scenario: POST /api/users/login matches the external contract
    Given a user is registered with email "contract@test.com" and password "testpass"
    When the user submits login with email "contract@test.com" and password "testpass"
    Then the response status is 200
    And the response body contains "token"
    And the response body contains "email"
    And the response body contains "username"
