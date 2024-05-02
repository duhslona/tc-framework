package com.example.teamcity.api.configs;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Config {
    private static Config config;

    private static final String CONFIG_FILE = "config.properties";

    private Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties(CONFIG_FILE);
    }

    private static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private void loadProperties(String fileName) {
        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                log.error("Config file not found" + fileName);
            }
            properties.load(stream);
        } catch (IOException e) {
            log.error("Error while reading file " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
