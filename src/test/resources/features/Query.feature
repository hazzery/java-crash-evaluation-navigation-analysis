Feature: Query Test
  Scenario: No filters
    Given I have no filters selected
    When I press apply
    Then All results in database are shown

  Scenario: Filter with car
    Given I have the car button selected
    When I press apply
    Then Only results with at least one car are shown