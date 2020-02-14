@google
Feature: Google Searches
  Feature to test automation structure

  @scenario1
  Scenario: Search for google
    Given I navigate to google
    When I search for "google"
    Then I validate the outcome

  @scenario2
  Scenario: Search for A
    Given I navigate to google
    When I search for "a"
    Then I validate the outcome

  @scenario3
  Scenario: Search for B
    Given I navigate to google
    When I search for "b"
    Then I validate the outcome

  @scenario4
  Scenario: Search for C
    Given I navigate to google
    When I search for "c"
    Then I validate the outcome
