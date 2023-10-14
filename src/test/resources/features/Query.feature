Feature: Query Test
  Scenario: No filters
    Given I have no filters selected
    When I press apply
    Then All results in database are shown

  Scenario: Filter with Pedestrian with fatal severity
    Given I have pedestrian selected
    And I have fatal severity selected
    When I press apply
    Then All results shown are of fatal severity
    And All results shown involve a pedestrian

  Scenario: Filter with Bay of plenty cyclist
    Given I have cyclist selected
    And I have Bay of plenty region selected
    When I press apply
    Then All results shown involve a cyclist
    And All results shown occurred in the Bay of Plenty

  Scenario: Filter with Buses between 2006 and 2016
    Given I have bus selected
    And I have the year slider set to 2006-2016
    When I press apply
    Then All results shown involve a bus
    And All results shown occurred between 2006-2016

  Scenario: Filter with Pedestrians or Buses with serious or fatal severity
    Given I have pedestrian and bus selected
    And I have serious and fatal severities selected
    When I press apply
    Then All results shown are of serious or fatal severity
    And All results shown involve a pedestrian or a bus


  Scenario: Filter with Cyclist or Car with no injury or minor severity in Auckland or Northland between 2018-2023
    Given I have bicycle and car selected
    And I have non injury and minor severities selected
    And I have Auckland and Northland regions selected
    And I have the year slider set to 2018-2023
    When I press apply
    Then All results shown involve a bicycle or car
    And All results shown are of non-injury or minor severity
    And All results shown occurred in either Auckland or Northland
    And All results shown occurred between 2018-2023

  Scenario: Apply filter then apply a separate filter
    Given I have pedestrian and bus selected
    And I have fatal severity selected
    And I press apply
    And I have bicycle and car selected
    And I have non injury and minor severities selected
    And I have Auckland and Northland regions selected
    And I have the year slider set to 2018-2023
    When I press apply
    Then All results shown involve a bicycle or car
    And All results shown are of non-injury or minor severity
    And All results shown occurred in either Auckland or Northland
    And All results shown occurred between 2018-2023

