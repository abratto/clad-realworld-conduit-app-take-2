@UC-03 @manage-profile
Feature: Manage Profile

  As a Member
  I want to view and update my profile fields
  So that I can keep my identity current

  Background:
    Given the system is running
    Given a registered user exists

  @manage-profile @happy-path
  Scenario: View own profile
    Given the user has a valid session token
    When the user requests GET /api/user with the token
    Then the response status is 200
    And the response body contains "username"
    And the response body contains "email"
    And the response body contains "token"

  @manage-profile @failure-path
  Scenario: View profile without token
    When the user requests GET /api/user without a token
    Then the response status is 401
    And the response body contains "is missing"

  @manage-profile @happy-path
  Scenario: Update bio in profile
    Given the user has a valid session token
    When the user submits PUT /api/user with bio "Updated bio" and token
    Then the response status is 200
    And the response body contains "Updated bio"
