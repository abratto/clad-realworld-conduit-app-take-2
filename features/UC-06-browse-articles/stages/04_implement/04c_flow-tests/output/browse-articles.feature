@UC-06 @browse-articles
Feature: Browse Articles

  As a Reader
  I want to discover articles filtered by tag, author, or favorited status
  So that I can find content I'm interested in

  Background:
    Given the system is running

  @browse-articles @happy-path
  Scenario: Browse articles without filters
    When the user requests GET /api/articles
    Then the response status is 200
    And the response body contains "articles"
    And the response body contains "articlesCount"
