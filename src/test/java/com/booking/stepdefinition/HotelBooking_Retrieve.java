package com.booking.stepdefinition;

import com.booking.util.BookingContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;

@RequiredArgsConstructor
public class HotelBooking_Retrieve {

    private final BookingContext context;
    Response response;
    @When("the user retrieves booking with booking ID")
    public void theUserRetrievesBookingWithBookingID() {

        //        As response for create booking is empty so we will get the
        //        booking id using get booking details by roomID
        String tokenValue = context.getToken();
        final int roomId = Integer.parseInt(BookingContext.getRoomIds().getLast().toString());
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);

        //        saving the value of bookingID
        BookingContext.addBookingId(context.response.jsonPath().get("bookings[0].bookingid"));


        final Integer storedBookingId = BookingContext.getBookingIds().getLast();
        if (storedBookingId != null) {
            context.response = context.requestSetup().cookie("token", tokenValue).when()
                    .get(context.session.get("endpoint").toString() +"/"+ storedBookingId);

            System.out.println("The user retrieves booking with booking ID: " + context.response.body());
        } else {
            throw new RuntimeException("Booking ID not available for GET request.");
        }
    }

    @Then("the response body should contain the response with JSON schema {string}")
    public void theResponseBodyShouldContainBookingDetailsWithID(final String schemaFileName) {
        context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }

    @Then("user should get the response code {int}")
    public void userShouldGetTheResponseCode(final Integer statusCode) {
        assertThat(Long.valueOf(statusCode)).isEqualTo(Long.valueOf(context.response.getStatusCode()));
    }

    @And("the response body should contain the correct values")
    public void theResponseBodyShouldContainTheCorrectValues() {
        System.out.println("response body >>"+context.response.body().asString());
        String name = context.response.jsonPath().get("bookings[0].firstname").toString();
        assertThat(name).isEqualTo("Ankita");
        String lastname = context.response.jsonPath().get("bookings[0].lastname").toString();
        assertThat(lastname).isEqualTo("Chouksey");
    }

    @When("the user retrieves booking with invalid booking ID {int}")
    public void theUserRetrievesBookingWithInvalidBookingID(final Integer bookingid) {
        String tokenValue = context.getToken();
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + bookingid);
    }

    @And("the response body should contain an empty bookings array")
    public void theResponseBodyShouldContainEmptyBookings() {
        context.response.then().assertThat().body("bookings", empty());
    }

    @When("the user retrieves booking with room ID")
    public void theUserRetrievesBookingWithRoomID() {
        final Integer roomId = BookingContext.getRoomIds().getLast();
        String tokenValue = context.getToken();
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);
        BookingContext.addBookingId(context.response.jsonPath().get("bookings[0].bookingid"));
        System.out.println("response with roomid>> : " + context.response.body().asString());
    }

    @When("the user retrieves booking with invalid room ID {int}")
    public void theUserRetrievesBookingWithInvalidRoomID(final Integer roomid) {
        String tokenValue = context.getToken();
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomid);
    }

    @And("the response body should contain the response with correct values")
    public void theResponseBodyShouldContainTheResponseWithCorrectValues() {
        String name = context.response.jsonPath().get("bookings[0].firstname").toString();
        assertThat(name).isEqualTo("Aarya");
        String lastname = context.response.jsonPath().get("bookings[7].lastname").toString();
        assertThat(lastname).isEqualTo("Chouksey");
    }

    @And("the response body should contain the response with the correct values")
    public void theResponseBodyShouldContainTheResponseWithTheCorrectValues() {
        String name = context.response.jsonPath().get("bookings[0].firstname").toString();
        assertThat(name).isEqualTo("Ankita");
        String lastname = context.response.jsonPath().get("bookings[7].lastname").toString();
        assertThat(lastname).isEqualTo("Chouksey");
    }

    @And("validate the response with updated values")
    public void validateTheResponseWithUpdatedValues() {
        final Integer roomId = BookingContext.getRoomIds().getLast();
        String tokenValue = context.getToken();
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);
        System.out.println("response for retrieval with room ID >> "+context.response.asString());
        String name = context.response.jsonPath().get("bookings[0].firstname").toString();
        assertThat(name).isEqualTo("Aarya");
        String lastname = context.response.jsonPath().get("bookings[7].lastname").toString();
        assertThat(lastname).isEqualTo("Chouksey");
    }
}
