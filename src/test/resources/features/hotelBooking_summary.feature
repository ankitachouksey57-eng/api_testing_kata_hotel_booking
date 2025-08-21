@bookingSummary @noAuth
Feature: Booking Summary API Tests

  Background:
    Given I have a valid token

#These scenario's are failing as it seems summary API is sending null response
  @getBookingSummaryByValidID
  Scenario Outline: Verify booking summary for different room ids
    Given user has access to endpoint "/booking"
    When the user books a room with the following booking details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    Then the response status code should be 200
    When the user retrieves booking summary "/booking/summary" with roomid
    Then the response should contain the booking dates "<checkin>" and "<checkout>" for room id and status code 200
    Examples:
      | firstname | lastname | email                | phone        | checkin    | checkout   |
      | Ankita    | Chouksey | ankichou@gmail.com   | 475871872462 | 2025-09-12 | 2025-09-18 |

  @getBookingSummaryByInvalidID
  Scenario: Verify booking summary for invalid room id
    Given user has access to endpoint "/booking"
    When the user retrieves booking summary "/booking/summary" with invalid roomid 0
    Then user should get the response code 200
    And the response body should contain an empty bookings array