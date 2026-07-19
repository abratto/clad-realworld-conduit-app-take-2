@UC-05 @manage-articles
Feature: Manage Articles

  As a Member
  I want to create, update, and delete my own articles
  So that I can publish and maintain my content

  Background:
    Given the system is running

  @manage-articles @happy-path
  Scenario: Create a new article
    Given the user has a valid session token
    When the user submits POST /api/articles with title "Test Title", description "Test Description", body "Test Body"
    Then the response status is 201
    And the response body contains "slug"
    And the response body contains "Test Title"

  @manage-articles @failure-path
  Scenario: Create article without authentication
    When the user submits POST /api/articles without a token
    Then the response status is 401

  @manage-articles @happy-path
  Scenario: Delete an article
    Given an article exists
    Given the user has a valid session token
    When the user submits DELETE /api/articles/test-slug
    Then the response status is 204
