@UC-11 @follow-user
Feature: Follow User

  As a Member
  I want to follow and unfollow other members
  So that articles by followed users appear in my feed

  Background:
    Given the system is running

  @follow-user @happy-path
  Scenario: Follow a user
    Given the user has a valid session token
    Given a user exists with username "target_user"
    When the user submits POST /api/profiles/target_user/follow with a valid token
    Then the response status is 200
    And the response body contains "following"

  @follow-user @happy-path
  Scenario: Unfollow a user
    Given the user has a valid session token
    Given a user exists with username "target_user"
    When the user submits DELETE /api/profiles/target_user/follow with a valid token
    Then the response status is 200
