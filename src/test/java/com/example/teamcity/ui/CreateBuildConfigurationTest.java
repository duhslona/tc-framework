package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Roles;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.specs.Specifications;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigPage;
import com.example.teamcity.ui.pages.admin.ProjectBuildsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateBuildConfigurationTest extends BaseUiTest {

    private TestData testData;
    private String url = "https://github.com/duhslona/template";


    @BeforeEach
    public void createProject() {
        testData = testDataStorage.addTestData();
        testData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.SYSTEM_ADMIN, "g"));
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewBuildConfiguration() {
        loginAsUser(testData.getUser());

        new CreateBuildConfigPage()
                .open(testData.getProject().getId())
                .waitUntilCreateBuildConfigPageIsLoaded()
                .createBuildConfigByUrl(url)
                .setupBuildConfig(testData.getBuildType().getName());

        new ProjectBuildsPage().open(testData.getProject().getId())
                .getBuildConfig().shouldHave(Condition.text(testData.getBuildType().getName()));
    }

}
