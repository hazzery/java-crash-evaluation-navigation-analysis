Feature: Query Test
  Scenario: Filter with car
    Given I have the car button selected
    When I press apply
    Then Only results with at least one car are shown