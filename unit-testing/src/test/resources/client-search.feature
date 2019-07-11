@txn
Feature: Search for clients

  # by name like
  # by IBAN like
  # by birth date
  # by nationality ISO CSV
  # sort

  Background:
    Given A Client exists in DB

  Scenario: By name matches
    Given The Client has name "John Doe"
    When Search criteria name="j"
    Then The Client is returned

  Scenario: By name doesn't match
    Given The Client has name "John Doe"
    When Search criteria name="x"
    Then No results are returned

  Scenario: By birth date
    Given The Client has birthDate "2000-01-01"
    And Today is "2019-01-01"
    When Search criteria min age="19"
    Then The Client is returned

  Scenario: By birth date
    Given The Client has birthDate "2000-01-01"
    And Today is "2019-01-01"
    When Search criteria max age="18"
    Then No results are returned

  Scenario: By birth date
    Given The Client has birthDate "2000-01-01"
    And Today is "2019-01-01"
    When Search criteria min age="18"
    And Search criteria max age="20"
    Then The Client is returned

  Scenario Outline: Birth Date Matrix
    Given The Client has birthDate "2000-01-01"
    And Today is "2019-01-01"
    When Search criteria min age="<minAge>"
    And Search criteria max age="<maxAge>"
    Then The Client is returned: "<matches>"
    Examples:
      | minAge | maxAge | matches |
      | 18     | 20     | true    |
      | 18     | 19     | false   |
      | 18     |        | true    |
      | 20     |        | false   |

  Scenario: By nationality
    Given The Client has nationality iso "RO"
    When Search criteria nationality iso = "ES,RO,FR"
    Then The Client is returned

  Scenario: By nationality
    Given The Client has nationality iso "RO"
    When Search criteria nationality iso = "ES,FR"
    Then No results are returned

  Scenario: By iban
    Given The Client has an IBAN "ibbbbaannn"
    And The Client has an IBAN "xxyyzz"
    When Search criteria iban = "Yz"
    Then The Client is returned

  Scenario: By iban
    Given The Client has an IBAN "ibbbbaannn"
    And The Client has an IBAN "xxyyzz"
    When Search criteria iban = "cc"
    Then No results are returned

