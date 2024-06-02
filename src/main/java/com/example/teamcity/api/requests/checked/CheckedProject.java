package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProject extends Request implements CrudInterface {

    public CheckedProject(RequestSpecification requestSpecification) {
        super(requestSpecification);
    }

    @Override
    public Project create(Object description) {
        var project = new UncheckedProject(requestSpecification).create(description)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);

        TestDataStorage.getStorage().addCreatedProjectId(project.getId() != null ? project.getId() : project.getName());

        return project;
    }

    @Override
    public Object get(String id) {
        return new UncheckedProject(requestSpecification)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object update(String id, Object object) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return new UncheckedProject(requestSpecification)
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
