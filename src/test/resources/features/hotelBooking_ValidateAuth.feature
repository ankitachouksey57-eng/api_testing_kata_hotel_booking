@AuthValidationAPI
Feature: Delete bookings API Tests without authentication

  Background:
    Given I have a valid token

#    verify without auth user can not proceed with request
  @AuthValidation
  Scenario: User try to delete a booking by ID without auth
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                | phone        | checkin    | checkout   |
      | Ankita    | Chouksey | anktachouk@gmail.com | 467537657364 | 2025-09-11 | 2025-09-14 |
    Then the response status code should be 200
    When the user deletes the booking with booking ID without auth
    Then the user should get a response code of 401
