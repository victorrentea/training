Feature: Bowling Gmae

  Background:
    Given: Current user is 'test'
    Given A mock Score String Provider

  Scenario: Initial Bowling Game
    When The score string is '--'
    Then The final score is 0


  Scenario: Initial Bowling Game
    When The score string is '-1'
    Then The final score is 1


  Scenario: Initial Bowling Game
    When The score string is '1-'
    Then The final score is 1

  Scenario Outline:
    When The score string is '<scoreString>'
    Then The final score is <totalScore>

    Examples:
      | scoreString          | totalScore |
      | 9-9-9-9-9-9-9-9-9-9- | 90         |
      | -------------------- | 0          |