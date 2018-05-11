#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: Tennis Score

  Scenario: Initial Score
    Given An empty game
    Then The score is "Love-Love"

  Scenario: Fifteen-Love
    Given An empty game
    When Player1 scores a point
    Then The score is "Fifteen-Love"

  Scenario: Fifteen-Fifteen
    Given An empty game
    When Player1 scores a point
    And Player2 scores a point
    Then The score is "Fifteen-Fifteen"

  ############## VARIATION ##################
  Scenario: Fifteen-Fourty
    Given An empty game
    When Player1 scores 1 points
    And Player2 scores 3 points
    Then The score is "Fifteen-Forty"

  ############## SCENARIO OUTLINE (data table) ##################
  Scenario Outline: A Record matches the filter
    Given An empty game
    When Player1 scores <player1points> points
    And Player2 scores <player2points> points
    Then The score is "<expectedScore>"

    Examples: 
      | player1points | player2points | expectedScore |
      |             0 |             0 | Love-Love     |
      |             1 |             0 | Fifteen-Love     |
      |             2 |             0 | Thirty-Love     |
