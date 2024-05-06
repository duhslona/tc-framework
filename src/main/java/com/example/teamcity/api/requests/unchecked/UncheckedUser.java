package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedUser extends Request implements CrudInterface {

    private final static String USER_ENDPOINT = "/app/rest/users";

    public UncheckedUser(RequestSpecification specification) {
        super(specification);
    }

    @Override
    public Response create(Object user) {
        return given()
                .spec(requestSpecification)
                .body(user)
                .post(USER_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(String id, Object object) {
        return null;
    }

    @Override
    public Response delete(String name) {
        return given()
                .spec(requestSpecification)
                .delete(USER_ENDPOINT + "/username:" + name);
    }
}
