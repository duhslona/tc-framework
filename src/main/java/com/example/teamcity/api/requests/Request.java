package com.example.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;

public class Request {
    protected RequestSpecification requestSpecification;

    public Request(RequestSpecification specification) {
        this.requestSpecification = specification;
    }
}
