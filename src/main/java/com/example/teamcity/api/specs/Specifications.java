package com.example.teamcity.api.specs;

import com.example.teamcity.api.configs.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static utils.TokenUtils.generateBasicAuthToken;

public class Specifications {

    private static Specifications spec;

    private Specifications() {
    }

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder requestBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        requestBuilder.setBaseUri(Config.getProperty("host"));

        return requestBuilder;
    }

    public RequestSpecification unauthSpec() {
        var requestBuilder = requestBuilder();

        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        var requestBuilder = requestBuilder();
        requestBuilder.addHeader("Authorization", generateBasicAuthToken(user.getUsername(), user.getPassword()));

        return requestBuilder.build();
    }

    public RequestSpecification superUserSpec() {
        var requestBuilder = requestBuilder();
        requestBuilder.addHeader("Authorization", generateBasicAuthToken("", Config.getProperty("superUserToken")));
        return requestBuilder.build();
    }
}
