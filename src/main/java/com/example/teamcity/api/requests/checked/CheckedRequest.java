package com.example.teamcity.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequest {

    private CheckedUser userRequest;
    private CheckedProject projectRequest;
    private CheckedBuildConfig buildConfigRequest;

    public CheckedRequest(RequestSpecification requestSpecification) {
        this.userRequest = new CheckedUser(requestSpecification);
        this.buildConfigRequest = new CheckedBuildConfig(requestSpecification);
        this.projectRequest  = new CheckedProject(requestSpecification);
    }
}
