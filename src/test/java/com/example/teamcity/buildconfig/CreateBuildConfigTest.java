package com.example.teamcity.buildconfig;

import com.example.teamcity.BaseApiTest;
import com.example.teamcity.api.enums.Roles;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.specs.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class CreateBuildConfigTest extends BaseApiTest {

    private TestData testData;

    @BeforeEach
    public void setupProject() {
        testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
    }

    @Nested
    class CreateBuildConfigDifferentRolesTests {
        @Test
        public void canNotCreateBuildConfigWithoutAuthorization() {
            var testData = testDataStorage.addTestData();
            checkedWithSuperUser.getProjectRequest().create(testData.getProject());

            testData.getUser().setRoles(TestDataGenerator
                    .generateRoles(Roles.PROJECT_ADMIN, String.format("p:%s", testData.getProject().getId())));

            checkedWithSuperUser.getUserRequest().create(testData.getUser());

            new UncheckedBuildConfig(Specifications.getSpec().unauthSpec()).create(testData.getBuildType())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        @Test
        public void projectAdminShouldHaveRightsToCreateBuildConfigInHisProject() {
            var testData = testDataStorage.addTestData();
            checkedWithSuperUser.getProjectRequest().create(testData.getProject());

            testData.getUser().setRoles(TestDataGenerator
                    .generateRoles(Roles.PROJECT_ADMIN, String.format("p:%s", testData.getProject().getId())));

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

    @Nested
    class ValidateBuildTypeIdInputTests {
        @Test
        public void couldCreateBuildTypeWithOneSymbolInId() {
            testData.getBuildType().setId(RandomData.getString(1));

            var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

            softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
        }

        @Test
        public void couldCreateBuildTypeWithMaxSymbolsInId() {
            testData.getBuildType().setId(RandomData.getString(225));

            var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

            softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
        }

        @Test
        public void couldNotCreateBuildTypeWithExceedMaxSymbolsInId() {
            testData.getBuildType().setId(RandomData.getString(226));

            uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
            //but actually there is 500 error that would be a bug if we really worked on this project
        }

        @Test
        public void buildConfigIdIsComposedCorrectlyIfNullIdInRequest() {
            testData.getBuildType().setId(null);

            var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

            softy.assertThat(buildConfig.getId()).isEqualTo(testData.getProject().getId() + "_" + testData.getBuildType().getName());
        }
    }

    @Nested
    class ValidateBuildTypeNameInputTests {
        @Test
        public void couldCreateBuildTypeWithOneSymbolInName() {
            testData.getBuildType().setName(RandomData.getString(1));

            var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

            softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
            softy.assertThat(buildConfig.getName()).isEqualTo(testData.getBuildType().getName());
        }

        @Test
        public void couldCreateBuildTypeWithManySymbolsInName() {
            testData.getBuildType().setName(RandomData.getString(1000));

            var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

            softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
            softy.assertThat(buildConfig.getName()).isEqualTo(testData.getBuildType().getName());
        }

        @ParameterizedTest
        @NullAndEmptySource
        public void couldNotCreateBuildTypeWithNullAndEmptyStringInName(String name) {
            testData.getBuildType().setName(name);

            uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }
    }
}
