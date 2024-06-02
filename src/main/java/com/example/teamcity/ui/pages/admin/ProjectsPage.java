package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

public class ProjectsPage extends FavoritePage {

    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private ElementsCollection subprojects = elements(Selectors.byClass("Subproject__container--Px"));


//    private ElementsCollection subprojects = elements(Selectors.byClass("ring-input-input"));
//    private ElementsCollection subprojects = elements(Selectors.byXPath("//div[contains(@class, 'Subproject__container')]"));
//    private SelenideElement header1 = element(Selectors.byClass("ring-input-input"));
//    private SelenideElement header2 = element(Selectors.byClass("Details__heading--id"));

    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<ProjectElement> getSubprojects() {
        return generatePageElements(subprojects, ProjectElement::new);
    }

}
