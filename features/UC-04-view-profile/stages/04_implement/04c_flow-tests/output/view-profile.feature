@UC-04 @view-profile
Feature: View Profile

  As a Reader
  I want to view a Member's public profile by username
  So that I can see their bio, image, and whether I follow them

  Background:
    Given the system is running

  @view-profile @happy-path
  Scenario: View a profile without authentication
    Given a user exists with username "existing_user"
    When the user requests GET /api/profiles/existing_user
    Then the response status is 200
    And the response body contains "username"
    And the response body contains "following"

  @view-profile @failure-path
  Scenario: View a non-existent profile
    When the user requests GET /api/profiles/nobody
    Then the response status is 404
    And the response body contains "not found"
