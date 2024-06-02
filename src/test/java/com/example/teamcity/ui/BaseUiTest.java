package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.configs.Config;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.specs.Specifications;
import com.example.teamcity.ui.pages.LoginPage;
import org.junit.jupiter.api.BeforeAll;

public class BaseUiTest extends BaseTest {

    @BeforeAll
    public static void setupIUTests() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = Config.getProperty("host");
//        Configuration.remote = Config.getProperty("remote");

        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";

        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        new LoginPage().open().login(user);
    }

    public void createUserAndLogin(User user) {
        new CheckedUser(Specifications.getSpec().superUserSpec()).create(user);
        new LoginPage().open().login(user);
    }
}
