@bookingAPI @updateBooking
Feature: Booking Update API Tests

  Background:
    Given I have a valid token

#  After Put_update execution ,booking details are not available through Booking Id and Room Id
#  hence scenario is failing in response validation
  @bookingUpdateSuccess
  Scenario: Successfully update an existing booking with valid data
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                  | phone         | checkin    | checkout   |
      | Ankita    | Chouksey | ankitachouks@gmail.com | 475871872462  | 2025-09-12 | 2025-09-18 |

    Then the response status code should be 200
    When the user updates the booking with following booking details
      | firstname | lastname | email                   | phone         | checkin    | checkout   |
      | Aarya   | Chouksey    | aaryachoukey@gmail.com | 4758718724625 | 2025-09-12 | 2025-09-18 |
    Then the response status code should be 200
    And validate the response with updated values

  @bookingUpdateError
  Scenario Outline: Update an existing booking with invalid data
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname | lastname | email                  | phone         | checkin    | checkout   |
      | Ankita    | Chouksey | ankitachouks@gmail.com | 475871872462  | 2025-09-12 | 2025-09-18 |

    Then the response status code should be 201
    When the user updates the booking with following booking details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then the response status code should be 400
    And the user should see response with incorrect "<FieldError>"

    Examples:
      | firstname | lastname | email                  | phone         | checkin    | checkout   | FieldError                            |
      |           | Chouksey | Ankitachouke@gmail.com | 4726453736473 | 2025-09-12 | 2025-09-18 | [Firstname should not be blank]       |
      | Ankita    | Ch       | Ankitachouke@gmail.com | 4726453736473 | 2025-09-12 | 2025-09-18 | [size must be between 3 and 30]       |
      | Ankita    | Chouksey | Ankitac                | 4726453736473 | 2025-09-12 | 2025-09-18 | [must be a well-formed email address] |
      | Ankita    | Chouksey | Ankitachouke@gmail.com | 4726453       | 2025-09-12 | 2025-09-18 | [size must be between 11 and 21]      |
      | Ankita    | Chouksey | Ankitachouke@gmail.com | 4726453736473 |            | 2025-09-18 | [must not be null]                    |
      | Ankita    | Chouksey | Ankitachouke@gmail.com | 4726453736473 | 2025-09-12 |            | [must not be null]                    |