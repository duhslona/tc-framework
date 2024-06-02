package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class CreateBuildConfigPage extends Page {

    private static final String CREATE_BUILD_CONFIGURATION_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=createBuildTypeMenu";
    private SelenideElement createFromUrlForm = element(Selectors.byId("createFromUrlForm"));
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement buildName = element(Selectors.byId("buildTypeName"));

    public CreateBuildConfigPage open(String projectId) {
        Selenide.open(String.format(CREATE_BUILD_CONFIGURATION_URL, projectId));
        waitUntilCreateBuildConfigPageIsLoaded();
        return this;
    }

    public CreateBuildConfigPage waitUntilCreateBuildConfigPageIsLoaded() {
        waitUntilPageIsLoaded();
        createFromUrlForm.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return this;
    }

    public CreateBuildConfigPage createBuildConfigByUrl(String url) {
        urlInput.shouldBe(Condition.visible, Duration.ofSeconds(30)).sendKeys(url);
        submit();
        waitUntilDataIsSaved();
        return this;
    }

    public void setupBuildConfig(String name) {
        buildName.shouldBe(Condition.visible, Duration.ofSeconds(30)).clear();
        buildName.sendKeys(name);
        submit();
        waitUntilDataIsSaved();
    }
}
