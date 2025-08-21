package com.booking.stepdefinition;

import com.booking.util.BookingContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
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
public class HotelBooking_Update {

    private JSONObject requestBody;
    private final BookingContext context;
    private static final Logger LOG = LogManager.getLogger(HotelBooking_Update.class);


    @When("the user updates the booking with following booking details")
    public void theUserUpdatesTheBookingWithFollowingDetails(final DataTable dataTable) {
        final Integer roomId = BookingContext.getRoomIds().getLast();
        String tokenValue = context.getToken();
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);
        System.out.println("response for retrieval with room ID >> "+context.response.asString());
        BookingContext.addBookingId(context.response.jsonPath().get("bookings[0].bookingid"));

        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {
            final String bookingId = BookingContext.getBookingIds().getLast().toString();
            BookingContext.addRoomId(Integer.parseInt(generateRandomRoomIds()));
            final Integer updatedroomId = BookingContext.getRoomIds().getLast();
            requestBody = createBookingRequestBodyForUpdate(data, updatedroomId);
            System.out.println("booking id retrieved>> "+bookingId);
            context.response = context.requestSetup().cookie("token", tokenValue).body(requestBody.toString())
                    .when().put(context.session.get("endpoint").toString() + "/"+ bookingId);

            System.out.println("response after update with booking ID >> "+context.response.asString());
        }
    }

    private JSONObject createBookingRequestBodyForUpdate(Map<String, String> row, int roomid) {
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


    private static String generateRandomRoomIds() {
        final Random random = new Random();
        return String.valueOf(3000 + random.nextInt(900)); // Generates a number between 3000 and 3999
    }

    @And("the user should have response with incorrect {string}")
    public void theUserShouldHaveResponseWithIncorrect(String errorMessage) {
        final String actualErrorMessage = context.response.jsonPath().getString("fieldErrors");
        if (actualErrorMessage.contains("[Firstname should not be blank, must not be null]")) {
            assertThat(errorMessage).isEqualTo(actualErrorMessage);
        }
        else if (actualErrorMessage.contains("[must not be null, Firstname should not be blank]")) {
            assertThat("[must not be null, Firstname should not be blank]").isEqualTo(actualErrorMessage);
        }
        else if (actualErrorMessage.contains("[Lastname should not be blank, must not be null]")) {
            assertThat(errorMessage).isEqualTo(actualErrorMessage);
        }
        else if (actualErrorMessage.contains("[must not be null, Lastname should not be blank]")) {
            assertThat("[must not be null, Lastname should not be blank]").isEqualTo(actualErrorMessage);
        }
        else
            assertThat(errorMessage).isEqualTo(actualErrorMessage);
    }
}
