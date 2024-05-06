package com.example.teamcity;

import com.example.teamcity.api.enums.Roles;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.specs.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RolesTest extends BaseApiTest {



    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigInHisProject() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.PROJECT_ADMIN, "p:" + testData.getProject().getId()));

        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser())).create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigInAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));
        checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());
        secondTestData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.PROJECT_ADMIN, "p:" + secondTestData.getProject().getId()));
        checkedWithSuperUser.getUserRequest().create(secondTestData.getUser());

        new UncheckedBuildConfig(Specifications.getSpec().authSpec(firstTestData.getUser()))
                .create(secondTestData.getBuildType()).then()
                .assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("You do not have enough permissions to edit project with id: "
                        + secondTestData.getProject().getId()));

    }
}
