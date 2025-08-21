package com.booking.stepdefinition;

import com.booking.util.BookingContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor
public class HotelBooking_Delete {

    private Response response;
    private final BookingContext context;


    // Step to send DELETE request
    @When("the user deletes the booking with booking ID")
    public void theUserDeletesTheBookingWithBookingID() {

//        As response for create booking is empty so we will get the
//        booking id using get booking details by roomID
        String tokenValue = context.getToken();
        final int roomId = Integer.parseInt(BookingContext.getRoomIds().getLast().toString());
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);

        System.out.println("response >>>"+context.response.body().asString());
//        saving the value of bookingID
        BookingContext.addBookingId(context.response.jsonPath().get("bookings[0].bookingid"));
        final Integer storedBookingId = BookingContext.getBookingIds().getLast();
        if (storedBookingId != null) {
            context.response = context.requestSetup().cookie("token", tokenValue).when()
                    .delete(context.session.get("endpoint").toString() + "/"+storedBookingId);
        } else {
            throw new RuntimeException("Booking ID not available for DELETE request.");
        }
    }

    @Then("the user should get a response code of {int}")
    public void theUserShouldGetResponseCodeOf(final int expectedStatusCode) {
        context.response.then().statusCode(expectedStatusCode);
    }

    @And("the booking should be deleted successfully")
    public void theBookingWithIdShouldBeDeletedSuccessfully() {
        // Attempt to retrieve the booking again to check if it was deleted (should return 404)

//        As response for create booking is empty so we will get the
//        booking id using get booking details by roomID
        String tokenValue = context.getToken();
        final int roomId = Integer.parseInt(BookingContext.getRoomIds().getLast().toString());
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);

        final Integer storedBookingId = BookingContext.getBookingIds().getLast();
        Response retrieveResponse = given().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() +storedBookingId);

        // Ensure the status code is 404 (not found), meaning the booking is deleted
        assertThat("Booking should be deleted", retrieveResponse.statusCode(), equalTo(404));
    }

    @When("the user deletes the booking with invalid booking ID {int}")
    public void theUserDeletesTheBookingWithInvalidBookingID(final int bookingid) {
        String tokenValue = context.getToken();
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .delete(context.session.get("endpoint").toString() +"/"+ "12");

    }
}
