package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.authSettings.AuthSettings;
import com.example.teamcity.api.requests.AuthSettingsRequest;
import com.example.teamcity.api.requests.checked.CheckedRequest;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import com.example.teamcity.api.specs.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public abstract class BaseApiTest extends BaseTest {

    protected TestDataStorage testDataStorage;
    protected UncheckedRequest uncheckedWithSuperUser = new UncheckedRequest(Specifications.getSpec().superUserSpec());
    protected CheckedRequest checkedWithSuperUser = new CheckedRequest(Specifications.getSpec().superUserSpec());

    @BeforeSuite
    public void activateRoles() {
        AuthSettings authSettings = new AuthSettingsRequest(Specifications.getSpec().superUserSpec()).get();
        authSettings.setPerProjectPermissions(true);
        new AuthSettingsRequest(Specifications.getSpec().superUserSpec()).update(authSettings);
    }

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

}
