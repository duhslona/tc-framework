package com.example.teamcity;

import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.specs.Specifications;
import org.testng.annotations.Test;


public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        new CheckedUser(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
