@UC-07 @read-article
Feature: Read Article
  Background: Given the system is running
  @read-article @happy-path
  Scenario: Read an article by slug
    Given an article exists
    When the user requests GET /api/articles/test-slug
    Then the response status is 200
    And the response body contains "slug"
