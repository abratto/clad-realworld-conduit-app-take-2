@UC-12 @view-feed
Feature: View Feed
  Background: Given the system is running
  @view-feed @failure-path
  Scenario: View feed without authentication
    When the user requests GET /api/articles/feed without a token
    Then the response status is 401
