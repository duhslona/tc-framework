package com.example.teamcity.project;

import com.example.teamcity.BaseApiTest;
import com.example.teamcity.api.enums.Roles;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.specs.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class CreateProjectTest extends BaseApiTest {

    @Nested
    class CreateProjectWithDifferentRolesTests {
        @Test
        public void superAdminShouldHaveRightsToCreateProject() {
            var testData = testDataStorage.addTestData();

            var project = new CheckedProject(Specifications.getSpec().superUserSpec())
                    .create(testData.getProject());

            new UncheckedProject(Specifications.getSpec().superUserSpec())
                    .get(project.getId())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_OK);
        }

        @Test
        public void systemAdminShouldHaveRightsToCreateProject() {
            var testData = testDataStorage.addTestData();

            testData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.SYSTEM_ADMIN, "g"));

            checkedWithSuperUser.getUserRequest().create(testData.getUser());

            var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser())).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        }

        @Test
        public void unauthorizedUserShouldNotHaveRightsToCreateProject() {
            var testData = testDataStorage.addTestData();

            new UncheckedProject(Specifications.getSpec().unauthSpec())
                    .create(testData.getProject())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);

            new UncheckedProject(Specifications.getSpec().superUserSpec())
                    .get(testData.getProject().getId())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                    .body(Matchers.containsString("No project found by locator 'count:1,id:" + testData.getProject().getId() + "'"));
        }

        @Test
        public void agentManagerShouldNotHaveRightsToCreateProject() {
            var testData = testDataStorage.addTestData();

            testData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.AGENT_MANAGER, "g"));

            checkedWithSuperUser.getUserRequest().create(testData.getUser());

            new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
                    .create(testData.getProject())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_FORBIDDEN);

            new UncheckedProject(Specifications.getSpec().superUserSpec())
                    .get(testData.getProject().getId())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                    .body(Matchers.containsString("No project found by locator 'count:1,id:" + testData.getProject().getId() + "'"));
        }

        @Test
        public void projectAdminShouldNotHaveRightsToCreateProjects() {
            var firstTestData = testDataStorage.addTestData();
            var secondTestData = testDataStorage.addTestData();
            checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());

            firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));
            checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

            new UncheckedProject(Specifications.getSpec().authSpec(firstTestData.getUser()))
                    .create(secondTestData.getProject())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_FORBIDDEN);

            new UncheckedProject(Specifications.getSpec().superUserSpec())
                    .get(secondTestData.getProject().getId())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                    .body(Matchers.containsString("No project found by locator 'count:1,id:" + secondTestData.getProject().getId() + "'"));
        }

        @Test
        public void projectDeveloperShouldNotHaveRightsToCreateProjects() {
            var firstTestData = testDataStorage.addTestData();
            var secondTestData = testDataStorage.addTestData();
            checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());

            firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.PROJECT_DEVELOPER, "p:" + firstTestData.getProject().getId()));
            checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

            new UncheckedProject(Specifications.getSpec().authSpec(firstTestData.getUser()))
                    .create(secondTestData.getProject())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_FORBIDDEN);

            new UncheckedProject(Specifications.getSpec().superUserSpec())
                    .get(secondTestData.getProject().getId())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                    .body(Matchers.containsString("No project found by locator 'count:1,id:" + secondTestData.getProject().getId() + "'"));
        }

        @Test
        public void projectViewerShouldNotHaveRightsToCreateProjects() {
            var firstTestData = testDataStorage.addTestData();
            var secondTestData = testDataStorage.addTestData();
            checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());

            firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(Roles.PROJECT_VIEWER, "p:" + firstTestData.getProject().getId()));
            checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

            new UncheckedProject(Specifications.getSpec().authSpec(firstTestData.getUser()))
                    .create(secondTestData.getProject())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_FORBIDDEN);

            new UncheckedProject(Specifications.getSpec().superUserSpec())
                    .get(secondTestData.getProject().getId())
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                    .body(Matchers.containsString("No project found by locator 'count:1,id:" + secondTestData.getProject().getId() + "'"));
        }

    }

    @Nested
    class ValidateProjectIdInputTests {

        @Test
        public void couldCreateProjectWithOneSymbolInId() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId(RandomData.getString(1));

            var project = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        }

        @Test
        public void nameIsUsedAsIdIfProjectWithNullIdCreated() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId(null);

            var project = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getName());
        }

        @Test
        public void couldNotCreateProjectWithNullNameAndNullId() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId(null);
            testData.getProject().setName(null);

            new UncheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

        @Test
        public void couldCreateProjectWithMaxSymbolsInId() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId(RandomData.getString(225));

            var project = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        }

        @Test
        public void couldNotCreateProjectWithExceedMaxSymbolsInId() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId(RandomData.getString(226));

            new UncheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
            //but actually there is 500 error that would be a bug if we really worked on this project
        }

        @Test
        public void couldNotCreateProjectWithEmptyStringInId() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId("");

            new UncheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
            //but actually there is 500 error that would be a bug if we really worked on this project
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", "!", "-", "?", "&"})
        public void couldNotCreateProjectWithProhibitedSymbolsInId(String symbol) {
            var testData = testDataStorage.addTestData();

            testData.getProject().setId(RandomData.getString() + symbol);

            new UncheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
            //but actually there is 500 error that would be a bug if we really worked on this project
        }
    }

    @Nested
    class ValidateProjectNameInputTests {

        @Test
        public void couldCreateProjectWithOneSymbolInName() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setName(RandomData.getString(1));

            var project = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
            softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
        }

        @Test
        public void couldCreateProjectWith1000SymbolsInName() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setName(RandomData.getString(1000));

            var project = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
            softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
        }

        //I didn't do the exceed max number test because my laptop was freezed before I found the maximum possible length

        @ParameterizedTest
        @NullAndEmptySource
        public void couldNotCreateProjectWithNullAndEmptyStringInName(String name) {
            var testData = testDataStorage.addTestData();

            testData.getProject().setName(name);

            new UncheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

        @Test
        public void couldNotCreateProjectWithNullAndEmptySpaceInName() {
            var testData = testDataStorage.addTestData();

            testData.getProject().setName(" ");

            new UncheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject())
                    .then().assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
            //but actually there is 500 error that would be a bug if we really worked on this project
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", "!", "_", "-", "?", "&"})
        public void couldCreateProjectWithDifferentSymbolsInName(String symbol) {
            var testData = testDataStorage.addTestData();

            testData.getProject().setName(RandomData.getString() + symbol);

            var project = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testData.getProject());

            softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
            softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
        }
    }

    @Nested
    class ValidateParentProjectInputTests {

        @Test
        public void couldCreateProjectWithExistedParentProject() {
            var testDataParent = testDataStorage.addTestData();
            var testDataChild = testDataStorage.addTestData();

            var parentProject = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testDataParent.getProject());

            testDataChild.getProject().setParentProject(parentProject);
            var childProject = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testDataChild.getProject());

            softy.assertThat(childProject.getId()).isEqualTo(testDataChild.getProject().getId());
            softy.assertThat(childProject.getParentProjectId()).isEqualTo(testDataParent.getProject().getId());
        }

        @Test
        public void defaultParentProjectIsUsedIfCreatedWithNotExistedParentProject() {
            var testDataParent = testDataStorage.addTestData();
            var testDataChild = testDataStorage.addTestData();

            testDataChild.getProject().setParentProject(testDataParent.getProject().getParentProject());
            var childProject = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testDataChild.getProject());

            softy.assertThat(childProject.getId()).isEqualTo(testDataChild.getProject().getId());
            softy.assertThat(childProject.getParentProjectId()).isEqualTo("_Root");
        }

        @Test
        public void defaultParentProjectIsUsedIfCreatedWithNullParentProject() {
            var testDataChild = testDataStorage.addTestData();

            testDataChild.getProject().setParentProject(null);
            var childProject = new CheckedProject(Specifications.getSpec().superUserSpec()).create(testDataChild.getProject());

            softy.assertThat(childProject.getId()).isEqualTo(testDataChild.getProject().getId());
            softy.assertThat(childProject.getParentProjectId()).isEqualTo("_Root");
        }
    }
}
