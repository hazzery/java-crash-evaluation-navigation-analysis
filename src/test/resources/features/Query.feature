Feature: Query Test
  Scenario: No filters
    Given I have no filters selected
    When I press apply
    Then All results in database are shown

  Scenario: Filter with Pedestrian with fatal severity
    Given I have pedestrian selected
    And I have fatal severity selected
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

  Scenario: Filter with Pedestrians or Buses with serious or fatal severity
    Given I have pedestrian and bus buttons and serious and fatal severities selected
    When I press apply
    Then All results shown involve a pedestrian or a bus with a serious or fatal severity

  Scenario: Filter with Cyclist or Car with no injury or minor severity in Auckland or Northland between 2018-2023
    Given I have bicycle and car buttons and non injury and minor severities and Auckland and Northland regions selected with the year range set to 2018-2023
    When I press apply
    Then All results shown involve a bicycle or car, with no injury or minor, in the Auckland or Northland regions between the years 2018 and 2023

  Scenario: Apply filter then apply a separate filter
    Given I have pedestrian and bus buttons and serious and fatal severities selected
    And I press apply
    And I have bicycle and car buttons and non injury and minor severities and Auckland and Northland regions selected with the year range set to 2018-2023
    When I press apply
    Then All results shown involve a bicycle or car, with no injury or minor, in the Auckland or Northland regions between the years 2018 and 2023
