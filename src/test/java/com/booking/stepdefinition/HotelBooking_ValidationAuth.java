package com.booking.stepdefinition;

import com.booking.util.BookingContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class HotelBooking_ValidationAuth {

    private final BookingContext context;
    private static final Logger LOG = LogManager.getLogger(HotelBooking_ValidationAuth.class);

    @When("the user deletes the booking with booking ID without auth")
    public void theUserDeletesTheBookingWithBookingIDWithoutAuth() {
        String tokenValue = context.getToken();
        final int roomId = Integer.parseInt(BookingContext.getRoomIds().getLast().toString());
        context.response = context.requestSetup().cookie("token", tokenValue).when()
                .get(context.session.get("endpoint").toString() + "?roomid=" + roomId);

        System.out.println("response >>>"+context.response.body().asString());
//        saving the value of bookingID
        BookingContext.addBookingId(context.response.jsonPath().get("bookings[0].bookingid"));
        final Integer storedBookingId = BookingContext.getBookingIds().getLast();
        if (storedBookingId != null) {
            context.response = context.requestSetup().when()
                    .delete(context.session.get("endpoint").toString() + "/"+storedBookingId);
            System.out.println("response after delete with booking ID >> "+context.response.body().asString());
            System.out.println("response after delete with booking ID >> "+context.response.statusCode());
        } else {
            throw new RuntimeException("Booking ID not available for DELETE request.");
        }
    }
}
