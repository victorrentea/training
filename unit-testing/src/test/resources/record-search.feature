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
@txn
Feature: Records Search

  Scenario Outline: Search for an Active Record by Version
    Given A Record with regliss reference "001.1.000001" with the following names in versions
      | V1 | V2 | V3 | V4 |
      | a1 | a2 | b  | b  |
    When criteria record name "<searchName>"
    And criteria version number <searchVerNo>
    And include historical records "<includeHistorical>"
    And include inactive records "<includeInactive>"
    Then the results have versions "<rowVersions>"
    And the row has regliss reference "001.1.000001"

    #a1 is inactive in V2; a2 is inactive in V3
    #inactive=true + hist=false will return a result only if the rec is INACTIVE in the LAST version
    # the change of name is NOT deactivation of Record
    Examples: 
      | searchName | searchVerNo | includeHistorical | includeInactive | rowVersions |
      | b          |             | true              | true            |         3,4 |
      | b          |             | true              | false           |         3,4 |
      | b          |             | false             | false           |           4 |
      | b          |             | false             | true            |           4 |
      | b          |           3 | false             | false           |           3 |
      | b          |           3 | false             | true            |           3 |
      | b          |           3 | true              | false           |           3 |
      | b          |           3 | true              | true            |           3 |
      | a%         |             | false             | true            |             |
      | a%         |             | false             | false           |             |
      | a%         |             | true              | false           |         1,2 |
      | a%         |             | true              | true            |         1,2 |
      | a%         |           1 | false             | false           |           1 |
      | a%         |           1 | false             | true            |           1 |
      | a%         |           1 | true              | false           |           1 |
      | a%         |           1 | true              | true            |           1 |

  Scenario Outline: Search for an Inactive Record by Version
    Given A Record with regliss reference "001.1.000001" with the following names in versions
      | V1 | V2 | V3 | V4 |
      | a1 | a2 | b  |    |
    When criteria record name "<searchName>"
    And criteria version number <searchVerNo>
    And include historical records "<includeHistorical>"
    And include inactive records "<includeInactive>"
    Then the results have versions "<rowVersions>"
    And the row has regliss reference "001.1.000001"

    #b is Active in V3
    #b is Inactive in V4
    #b is NOT present in V5
    #
    #Historical = look also in versions before the last one
    #Inactive = record is inactive in the version returned
    #
    Examples: 
      | searchName | searchVerNo | includeHistorical | includeInactive | rowVersions |
      | b          |             | true              | true            |         3,4 |
      | b          |             | false             | true            |           4 |
      | b          |             | true              | false           |           3 |
      | b          |             | false             | false           |             |
      | b          |           3 | false             | true            |           3 |
      | b          |           3 | true              | true            |           3 |
      | b          |           3 | false             | false           |           3 |
      | b          |           3 | true              | false           |           3 |
      | a%         |             | true              | true            |         1,2 |
      | a%         |             | false             | true            |             |
      | a%         |             | true              | false           |         1,2 |
      | a%         |             | false             | false           |             |
      | a%         |           1 | false             | true            |           1 |
      | a%         |           1 | true              | true            |           1 |
      | a%         |           1 | false             | false           |           1 |
      | a%         |           1 | true              | false           |           1 |

  Scenario Outline: Search for an Active Record by Version and activity interval
    Given A Record with regliss reference "001.1.000001" with the following names in versions
      | V1               | V2               | V3               | V4               |
      | a1               | a2               | b                | b                |
      | 2016-01-03 09:00 | 2016-01-10 09:00 | 2016-01-20 09:00 | 2016-01-29 00:00 |
    When criteria record name "<searchName>"
    And criteria version number <searchVerNo>
    And include historical records "<includeHistorical>"
    And record active from "2016-01-<from>"
    And record active to "2016-01-<to>"
    Then the results have versions "<rowVersions>"
    And the row has regliss reference "001.1.000001"

    Examples: 
      | searchName | searchVerNo | from | to | includeHistorical | rowVersions |
      | b          |             |   29 | 29 | true              |           4 |
      | b          |             |   29 | 29 | false             |           4 |
      | b          |             |   30 | 31 | false             |           4 |
      | b          |           3 |   20 | 29 | true              |           3 |
      | b          |           3 |   30 | 31 | true              |             |
      | a%         |             |   10 | 10 | true              |         1,2 |
      | a%         |             |   01 | 09 | true              |           1 |
      | a%         |             |   20 | 30 | true              |           2 |
      | a%         |             |   11 | 18 | true              |           2 |
      | a%         |           1 |   10 | 10 | true              |           1 |
      | a%         |           1 |   01 | 09 | true              |           1 |
      | a%         |           1 |   20 | 30 | true              |             |
      | a%         |           1 |   03 | 03 | true              |           1 |
      | a%         |           1 |   11 | 19 | true              |             |
      | a%         |           1 |   01 | 09 | true              |           1 |
      | b          |             |   30 | 31 | true              |           4 |

  Scenario Outline: Search for an Inactive Record by Version and activity interval
    Given A Record with regliss reference "001.1.000001" with the following names in versions
      | V1               | V2               | V3               | V4               |
      | a1               | a2               | b                |                  |
      | 2016-01-03 09:00 | 2016-01-10 09:00 | 2016-01-20 09:00 | 2016-01-29 00:00 |
    When criteria record name "<searchName>"
    And criteria version number <searchVerNo>
    And include inactive records "true"
    And include historical records "true"
    And record active from "2016-01-<from>"
    And record active to "2016-01-<to>"
    Then the results have versions "<rowVersions>"
    And the row has regliss reference "001.1.000001"

    Examples: 
      | searchName | searchVerNo | from | to | rowVersions |
      | b          |             |   20 | 20 |           3 |
      | b          |             |   30 | 30 |           4 |
      | b          |           3 |   20 | 29 |           3 |
      | b          |           3 |   30 | 30 |             |
      | a%         |             |   10 | 10 |         1,2 |
      | a%         |             |   01 | 09 |           1 |
      | a%         |             |   20 | 30 |           2 |
      | a%         |             |   11 | 19 |           2 |
      | a%         |           1 |   10 | 10 |           1 |
      | a%         |           1 |   01 | 09 |           1 |
      | a%         |           1 |   20 | 30 |             |
      | a%         |           1 |   03 | 03 |           1 |
      | a%         |           1 |   11 | 19 |             |
      | b          |             |   25 | 30 |         3,4 |
