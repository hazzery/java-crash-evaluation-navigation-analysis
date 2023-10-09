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