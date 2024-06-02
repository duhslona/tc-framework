package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class ProjectPage extends Page {

    private static final String Project_CONFIGS_URL = "/project/%s";
    private SelenideElement header = element(Selectors.byClass("ProjectPageHeader__title--ih"));
    private SelenideElement editProjectButton = element(Selectors.byDataTest("ring-button-group"));

    public ProjectPage open(String projectId) {
        Selenide.open(String.format(Project_CONFIGS_URL, projectId));
        waitUntilProjectPageIsLoaded();
        return this;
    }

    public ProjectPage waitUntilProjectPageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return this;
    }

}
