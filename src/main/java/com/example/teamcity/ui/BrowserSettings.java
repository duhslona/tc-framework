package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class BrowserSettings {

    public static void setup(String browser) {
        Configuration.browser = browser;
        Map<String, Boolean> options = getSelenoidOptions();

        switch (browser) {
            case "firefox":
                setFirefoxOptions(options);
                break;
            case "chrome":
                setChromeOptions(options);
                break;
        }
    }

    private static void setFirefoxOptions(Map<String, Boolean> options) {
        Configuration.browserCapabilities = new FirefoxOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", options);
    }

    private static void setChromeOptions(Map<String, Boolean> options) {
        Configuration.browserCapabilities = new ChromeOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", options);
    }

    private static Map<String, Boolean> getSelenoidOptions() {
        Map<String, Boolean> options = new HashMap<>();
        options.put("enableVNC", true);
        options.put("enableLog", true);
        return options;
    }
}
