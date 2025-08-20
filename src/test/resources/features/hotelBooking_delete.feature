@bookingAPI @deleteBooking
Feature: Delete bookings API Tests

  Background:
    Given I have a valid token

  @deleteBookingByValidId
  Scenario: User deletes a booking by ID
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                | phone        | checkin    | checkout   |
      | Ankita    | Chouksey | anktachouk@gmail.com | 467537657364 | 2025-09-11 | 2025-09-14 |
    Then the response status code should be 200
    When the user deletes the booking with booking ID
    Then the user should get a response code of 200
    And the booking should be deleted successfully

  @deleteBookingByInvalidId
  Scenario: User deletes a booking by ID
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                | phone        | checkin    | checkout   |
      | Ankita    | Chouksey | anktachouk@gmail.com | 467537657364 | 2025-09-11 | 2025-09-14 |
    Then the response status code should be 200
    When the user deletes the booking with invalid booking ID 12
    Then the user should get a response code of 500
