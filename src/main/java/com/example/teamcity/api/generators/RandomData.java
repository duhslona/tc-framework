package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {

    private static final int DEFAULT_LENGTH = 10;

    public static String getString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getString() {
        return getString(DEFAULT_LENGTH);
    }
}
