Feature: Query Test
  Scenario: No filters
    Given I have no filters selected
    When I press apply
    Then All results in database are shown

  Scenario: Filter with car
    Given I have person and fatal severity selected
    When I press apply
    Then All results shown involve a pedestrian and a fatality