package com.example.teamcity.api.requests.unchecked;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequest {

    private UncheckedUser userRequest;
    private UncheckedProject projectRequest;
    private UncheckedBuildConfig buildConfigRequest;

    public UncheckedRequest(RequestSpecification requestSpecification) {
        this.userRequest = new UncheckedUser(requestSpecification);
        this.buildConfigRequest = new UncheckedBuildConfig(requestSpecification);
        this.projectRequest  = new UncheckedProject(requestSpecification);
    }
}
