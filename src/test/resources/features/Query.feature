Feature: Query Test
  Scenario: No filters
    Given I have no filters selected
    When I press apply
    Then All results in database are shown

  Scenario: Filter with fatal pedestrian
    Given I have person and fatal severity selected
    When I press apply
    Then All results shown involve a pedestrian and a fatality

  Scenario: Filter with Bay of plenty cyclist
    Given I have cyclist and Bay of plenty region selected
    When I press apply
    Then All results shown involve a cyclist in the Bay of plenty

  Scenario: Filter with Buses between 2006 and 2016
    Given I have bus selected with the year slider set between 2006 and 2016
    When I press apply
    Then All results shown involve a bus between 2006 and 2016