package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.authSettings.AuthSettings;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthSettingsRequest {

    private static final String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";

    private final RequestSpecification requestSpecification;

    public AuthSettingsRequest(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public AuthSettings get() {
        return given()
                .spec(requestSpecification)
                .get(AUTH_SETTINGS_ENDPOINT)
                .then()
                .extract().as(AuthSettings.class);
    }

    public Response update(AuthSettings authSettings) {
        return given()
                .spec(requestSpecification)
                .body(authSettings)
                .put(AUTH_SETTINGS_ENDPOINT);
    }


}
