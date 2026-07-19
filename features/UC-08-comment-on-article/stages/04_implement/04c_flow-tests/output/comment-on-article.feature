@UC-08 @comment-on-article
Feature: Comment on Article
  Background: Given the system is running
  @comment-on-article @happy-path
  Scenario: Add a comment to an article
    Given a user is registered
    When the user adds a comment to an article
    Then the response status is 200
