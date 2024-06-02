package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedProject extends Request implements CrudInterface {

    private static final String PROJECTS_ENDPOINT = "/app/rest/projects";

    public UncheckedProject(RequestSpecification specification) {
        super(specification);
    }

    @Override
    public Response create(Object description) {
        return given()
                .spec(requestSpecification)
                .body(description)
                .post(PROJECTS_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given()
                .spec(requestSpecification)
                .get(PROJECTS_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(String id, Object object) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(requestSpecification)
                .delete(PROJECTS_ENDPOINT + "/id:" + id);
    }
}
