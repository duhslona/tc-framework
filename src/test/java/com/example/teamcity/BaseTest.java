package com.example.teamcity;


import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.authSettings.AuthSettings;
import com.example.teamcity.api.requests.AuthSettingsRequest;
import com.example.teamcity.api.requests.checked.CheckedRequest;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import com.example.teamcity.api.specs.Specifications;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected static TestDataStorage testDataStorage;
    protected UncheckedRequest uncheckedWithSuperUser = new UncheckedRequest(Specifications.getSpec().superUserSpec());
    protected CheckedRequest checkedWithSuperUser = new CheckedRequest(Specifications.getSpec().superUserSpec());
    protected SoftAssertions softy;

    @BeforeAll
    public static void activateRoles() {
        AuthSettings authSettings = new AuthSettingsRequest(Specifications.getSpec().superUserSpec()).get();
        authSettings.setPerProjectPermissions(true);
        new AuthSettingsRequest(Specifications.getSpec().superUserSpec()).update(authSettings);
    }

    @BeforeAll
    public static void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterAll
    public static void cleanTest() {
        testDataStorage.deleteCreated();
    }

    @BeforeEach
    public void beforeTest() {
        softy = new SoftAssertions();
    }

    @AfterEach
    public void afterTest() {
        softy.assertAll();
    }

}
