package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;

import static com.codeborne.selenide.Selenide.element;

public class ProjectBuildsPage extends ProjectPage {

    private static final String PROJECT_BUILDS_URL = "/project/%s?mode=builds#all-projects";

    private SelenideElement buildType = element(Selectors.byClass("MiddleEllipsis__middleEllipsis--Ei BuildTypeLine__caption--uj"));

    public ProjectBuildsPage open(String projectId) {
        Selenide.open(String.format(PROJECT_BUILDS_URL, projectId));
        waitUntilProjectPageIsLoaded();
        return this;
    }

    public SelenideElement getBuildConfig() {
        return buildType;
    }
}
