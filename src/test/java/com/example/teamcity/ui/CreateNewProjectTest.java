package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.admin.CreateNewProject;
import org.junit.jupiter.api.Test;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var url = "https://github.com/duhslona/template";

        var testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());
    }

}
