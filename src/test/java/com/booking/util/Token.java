package com.booking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Token {

    private BookingContext context;
    Credentials payload = new Credentials();
    private Response response;
    String token_created;
    public Token(BookingContext context){
        this.context = context;
    }

    @Given("I have a valid token")
    public String validToken() throws JsonProcessingException {

        payload.setUsername(PropertyFile.getProperty("username"));
        payload.setPassword(PropertyFile.getProperty("password"));
        // convert
        String TokenJSONformat = "";
        TokenJSONformat = SupportLibrary.convertJavaObjectToJson(payload);
        System.out.println();  //sout
        System.out.println("Token:  " +TokenJSONformat);

        response = given().contentType(ContentType.JSON)
                .accept("*/*")
                .body(TokenJSONformat)
                .log().all()
                .post("https://automationintesting.online/api/auth/login");

        token_created = response.jsonPath().get("token").toString();
        context.setToken(token_created);
        System.out.println("inside Token class:  "+token_created);
       return token_created;

    }
}
