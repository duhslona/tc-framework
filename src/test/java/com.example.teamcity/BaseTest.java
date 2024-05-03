package com.example.teamcity;


import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected SoftAssertions softy;

    @BeforeEach
    public void beforeTest() {
        softy = new SoftAssertions();
    }

    @BeforeEach
    public void afterTest() {
        softy.assertAll();
    }

}
