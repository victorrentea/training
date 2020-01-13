Feature: Render Board

  Scenario: Empty minefield
    Given A new game of size 5 x 3
    When I print the board
    Then the output should contain exactly:
      """
      .....
      .....
      .....
      """

  Scenario: Place Single Mine on small Gameboard

  Scenario: Place Single Mine on large Gameboard

  Scenario: Place Two Mines on large Gameboard

  Scenario: Place Three Mines on large Gameboard