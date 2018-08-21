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
  Scenario Outline: Un tabel de date
    Given An empty game
    When Player1 scores <player1points> points
    And Player2 scores <player2points> points
    Then The score is "<expectedScore>"

    Examples:
      | player1points | player2points | expectedScore     |
      | 0             | 1             | Love-Fifteen      |
      | 0             | 0             | Love-Love         |
      | 1             | 0             | Fifteen-Love      |
      | 2             | 0             | Thirty-Love       |
      | 3             | 3             | Deuce             |
      | 3             | 4             | Advantage Player2 |
