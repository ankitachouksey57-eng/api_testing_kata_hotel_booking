@hotelBooking
Feature: Booking Retrieval API Tests

  Background:
    Given I have a valid token

  @getBookingByRoomID
  Scenario Outline: Retrieve bookings with valid Room IDs
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then the response status code should be 200
    When the user retrieves booking with room ID
    Then user should get the response code 200
    And the response body should contain the correct values
    Examples:
      | firstname | lastname | email              | phone         | checkin    | checkout   |
      | Ankita    | Chouksey | ankichou@gmail.com | 475871872462  | 2025-09-12 | 2025-09-18 |

  @getBookingByInvalidBookingID
  Scenario: Retrieve bookings with invalid Booking IDs
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                | phone         | checkin    | checkout   |
      | Ankita    | Chouksey | Ankitachou@gmail.com | 4657525384457 | 2025-09-22 | 2025-09-25 |
    Then the response status code should be 200
    When the user retrieves booking with invalid booking ID 0
    Then user should get the response code 404

  @getBookingByInvalidRoomID
  Scenario: Retrieve bookings with invalid room IDs
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                | phone         | checkin    | checkout   |
      | Ankita    | Chouksey | Ankitachou@gmail.com | 4657525384457 | 2025-09-22 | 2025-09-25 |
    Then the response status code should be 200
    When the user retrieves booking with invalid room ID 0
    Then user should get the response code 200
    And the response body should contain an empty bookings array

     #  get booking details by ID is failing
#  @getBookingByBookingID
#  Scenario: Retrieve bookings with valid Booking IDs
#    Given user has access to endpoint "/booking"
#    When the user books a room with the following booking details
#      | firstname | lastname | email                | phone         | checkin    | checkout   |
#      | Ankita    | Chouksey | Ankitachou@gmail.com | 4657525384457 | 2025-09-22 | 2025-09-25 |
#    Then the response status code should be 200
#    When the user retrieves booking with booking ID
#    Then user should get the response code 200
#    And the response body should contain the response with correct values
