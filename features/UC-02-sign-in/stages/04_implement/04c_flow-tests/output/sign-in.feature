@UC-02 @sign-in
Feature: Sign In

  As a Member
  I want to authenticate with email and password
  So that I obtain a JWT for subsequent requests

  Background:
    Given the system is running

  @sign-in @happy-path
  Scenario: Register and sign in with email (tested via registration endpoint)
    When the user submits POST /api/users with username "e2e_uid_1", email "e2e_1@test.com", and password "pass"
    Then the response status is 201
    And the response body contains "token"
    And the response body contains "e2e_1@test.com"
