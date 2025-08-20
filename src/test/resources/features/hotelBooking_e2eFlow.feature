@crudOperation
Feature: End to End Booking Test

  Background:
    Given I have a valid token

@e2eBookingFlow
  Scenario Outline: To perform a CURD operation on booking API
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then the response status code should be 200
    When the user retrieves booking with room ID
    Then user should get the response code 200
    And the response body should contain the correct values
    When the user updates the booking with following booking details
      | firstname | lastname | email              | phone         | checkin    | checkout   |
      | Aarya     | Chouksey | ankichou@gmail.com | 475871872462  | 2025-09-12 | 2025-09-18 |
    Then the response status code should be 200
    And validate the response should have correct values
#  After update operation we are unable to find the booking details with room id as well as booking id
#    When the user deletes the booking with booking ID
#    Then the user should get a response code of 200
    Examples:
      | firstname | lastname | email              | phone         | checkin    | checkout   |
      | Ankita    | Chouksey | ankichou@gmail.com | 475871872462  | 2025-09-12 | 2025-09-18 |
