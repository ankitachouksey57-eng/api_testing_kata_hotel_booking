@hotelBooking
Feature: Hotel Room Booking API Tests

  Background:
    Given I have a valid token

  @successfulBookingCreation
  Scenario Outline: Create a booking with valid data successfully
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then the response status code should be 200
#    And validate the response with label values //response has empty body hence cannot be validated
    Examples:
      | firstname | lastname | email                    | phone        | checkin    | checkout   |
      | Ankita    | Chouksey | anktachouk@gmail.com     | 467537657364 | 2025-09-11 | 2025-09-14 |
      | Aarya     | Choukey  | aaryachouk@gmail.com     | 475867564532 | 2025-09-14 | 2025-09-20 |


  @negativeBookingCreation
  Scenario Outline: Create booking without passing required fields should fail
    Given user has access to endpoint "/booking"
    When the user tries to book a room with invalid booking details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   | bookingid   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> | <bookingid> |
    Then the response status code should be 400
    And the user should see response with incorrect "<FieldError>"
    Examples:
      | firstname | lastname | email              | phone        | checkin    | checkout   | FieldError                            |
      | Ankita    | Chouksey | ankichou@gmail.com | 47654398     | 2025-09-12 | 2025-09-16 | [size must be between 11 and 21]      |
      | Ankita    | Chouks   | ankichou@gmail.com | 476543986468 |            | 2025-09-16 | [must not be null]                    |
      | Aarya     | Chouksey | ankichou@gmail.com | 476543986468 | 2025-09-12 |            | [must not be null]                    |
      |           | Chouks   | ankichou@gmail.com | 476543986468 |            | 2025-09-16 | [Firstname should not be blank, must not be null]                    |
      | Ankita    |          | ankichou@gmail.com | 476543986468 |            | 2025-09-16 | [Lastname should not be blank, must not be null]                    |
      | Ankita    | chouksey |                    | 476543986468 |            | 2025-09-16 | [must not be null]                    |
