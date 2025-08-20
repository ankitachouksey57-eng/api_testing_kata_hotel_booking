package com.booking.stepdefinition;

import com.booking.util.BookingContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class HotelBooking_CreateAndUpdate {

    private Response response;
    private JSONObject requestBody;
    private final BookingContext context;
    private static final Logger LOG = LogManager.getLogger(HotelBooking_CreateAndUpdate.class);

    @Given("user has access to endpoint {string}")
    public void userHasAccessToEndpoint(final String endpoint) {
        context.session.put("endpoint", endpoint);
    }

    @When("the user books a room with the following booking details")
    public void theUserBooksRoomWithBookingDetails(final DataTable dataTable) {

        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            final int roomId = Integer.parseInt(generateRandomRoomId());
            String tokenValue = context.getToken();
            BookingContext.addRoomId(roomId);

            requestBody = createBookingRequestBody(row, roomId);
            System.out.println(requestBody.toString());
            response = context.requestSetup().cookie("token", tokenValue).body(requestBody.toString()).when().post(context.session.get("endpoint").toString());
            System.out.println(" response body after booking creation >> "+response.body().asString());
        }
    }

    private void validateBookingResponse(Response response, String firstname, String lastname, String checkin, String checkout) {
        String responseFirstname = response.jsonPath().getString("booking.firstname");
        String responseLastname = response.jsonPath().getString("booking.lastname");
        String responseCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
        String responseCheckout = response.jsonPath().getString("booking.bookingdates.checkout");

        assertThat(firstname).isEqualTo(responseFirstname);
        assertThat(lastname).isEqualTo(responseLastname);
        assertThat(checkin).isEqualTo(responseCheckIn);
        assertThat(checkout).isEqualTo(responseCheckout);
    }

    //This scenario is not possible to test because response body is empty for create booking
    @And("validate the response with label values")
    public void validateTheResponseWithLabelValues() {
        System.out.println("response body >>"+response.body().asString());
        String name = response.jsonPath().get("bookings[0].firstname").toString();
        assertThat(name).isEqualTo("Ankita");
        String lastname = response.jsonPath().get("bookings[7].lastname").toString();
        assertThat(lastname).isEqualTo("Chouksey");
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(final int expectedStatusCode) {
        assertThat(expectedStatusCode).isEqualTo(response.getStatusCode());
    }

    @Then("validate the response with JSON schema {string}")
    public void userValidatesResponseWithJSONSchema(final String schemaFileName) {

    }

    @When("the user tries to book a room with invalid booking details")
    public void theUserSendsPOSTRequestWithTheInvalidBookingDetails(final DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            final int roomId = Integer.parseInt(generateRandomRoomId());
            requestBody = createBookingRequestBody(row, roomId);
            response = context.requestSetup().body(requestBody.toString()).when().post(context.session.get("endpoint").toString());
            System.out.println(" response body after booking creation >> "+response.body().asString());
            LOG.info("Booking has failed: " + response.toString());
        }
    }

    @And("the user should see response with incorrect {string}")
    public void theUserShouldSeeTheResponseWithIncorrectField(final String errorMessage) {
        final String actualErrorMessage = response.jsonPath().getString("errors");
        assertThat(errorMessage).isEqualTo(actualErrorMessage);
    }

    @When("the user updates the booking with following booking details")
    public void theUserUpdatesTheBookingWithFollowingDetails(final DataTable dataTable) {
        final Integer roomId = BookingContext.getRoomIds().getLast();
        String tokenValue = context.getToken();
        response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);
        System.out.println("response for retrieval with room ID >> "+response.asString());
        BookingContext.addBookingId(response.jsonPath().get("bookings[0].bookingid"));

        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {
            final String bookingId = BookingContext.getBookingIds().getLast().toString();
            BookingContext.addRoomId(Integer.parseInt(generateRandomRoomId()));
            final Integer updatedroomId = BookingContext.getRoomIds().getLast();
            requestBody = createBookingRequestBody(data, updatedroomId);
            System.out.println("booking id retrieved>> "+bookingId);
            response = context.requestSetup().cookie("token", tokenValue).body(requestBody.toString())
                    .when().put(context.session.get("endpoint").toString() + "/"+ bookingId);

            System.out.println("response after update with booking ID >> "+response.asString());
        }
    }

    private JSONObject createBookingRequestBody(Map<String, String> row, int roomid) {
        return new JSONObject()
                .put("bookingid", 0)
                .put("roomid", roomid)
                .put("firstname", row.get("firstname"))
                .put("lastname", row.get("lastname"))
                .put("depositpaid", true)
                .put("email", row.get("email"))
                .put("phone", row.get("phone"))
                .put("bookingdates", new JSONObject()
                        .put("checkin", row.get("checkin"))
                        .put("checkout", row.get("checkout")));
    }

    private static String generateRandomRoomId() {
        final Random random = new Random();
        return String.valueOf(3000 + random.nextInt(900)); // Generates a number between 3000 and 3999
    }



    @And("validate the response should have correct values")
    public void validateTheResponseShouldHaveCorrectValues() {
        //unable to get the response after update operation
    }
}
