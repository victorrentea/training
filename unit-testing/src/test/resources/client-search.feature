@txn
Feature: Search for clients

  # LIKE name
  # IBAN like
  # date of birht
  # IN (country ISOs)
  # intors valutele concatenate
  #sort stabil

  Background:
    Given A Client exists in DB

  Scenario: Client Search
    Given The Client has name "John Doe"
    When Search criteria name="j"
    Then The Client is returned

  Scenario: Client Search NO Match
    Given The Client has name "John Doe"
    When Search criteria name="x"
    Then No results are returned


#  Scenario Outline: A Record matches the filter




#    Given The following descriptions exist:
#      #| Description1[Description2(Description3,..);..] |
#      | PEP[PA(PA1,PA2,PA3);PB(PB1,PB2);PC()] |
#      | RCA[]                                 |
#      | SIP[SA();SB(SB1);SC(SC1,SC2)]         |
#    And The following PEP Occupation categories exist: "Cat1,Cat2,Cat3"
#    When A Filter has Desc1;Desc2;Desc3;Occupation lists: "<FilterD1>"; "<FilterD2>"; "<FilterD3>"; "<FilterOccup>"
#    And A Record has Desc1;Desc2;Desc3;Occupation lists: "<RecordD1>"; "<RecordD2>"; "<RecordD3>"; "<RecordOccup>"
#    Then Does the record passes the filter? <RecordIsSelectedByFilter>
#
#    Examples:
#      | FilterD1 | FilterD2 | FilterD3 | FilterOccup | RecordD1 | RecordD2 | RecordD3 | RecordOccup | RecordIsSelectedByFilter |
#      |          |          |          |             | RCA      |          |          |             | yes                      |
#      | PEP      |          |          |             | RCA      |          |          |             | no                       |
#      | PEP      |          |          |             | PEP      |          |          |             | yes                      |
#      | PEP      |          |          |             | PEP,SIP  |          |          |             | yes                      |
#      | RCA,PEP  |          |          |             | PEP,SIP  |          |          |             | yes                      |
#      | PEP      |          |          | Cat1        | PEP      |          |          |             | no                       |
#      | PEP      |          |          | Cat1        | PEP      |          |          | Cat2,Cat1   | yes                      |
#      | PEP      | PA       |          | Cat1        | PEP      |          |          | Cat1        | no                       |
#      | PEP      |          |          |             | PEP      | PA       |          |             | yes                      |
#      | PEP      | PA       |          |             | PEP      |          |          |             | no                       |
#      | PEP      | PA       |          |             | PEP      | PA       |          |             | yes                      |
#      | PEP      | PA,PB    |          |             | PEP      | PC,PB    |          |             | yes                      |
#      | PEP      | PA,PB    | PB1      |             | PEP      | PA,PB    |          |             | yes                      |
#      | PEP      | PA       | PA1      |             | PEP      |          |          |             | yes                       |
