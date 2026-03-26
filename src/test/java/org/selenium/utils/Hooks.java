package org.selenium.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    /**
     * @param scenario
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Driver initialized for scenario : " + scenario.getName());
        SeleniumDriverFactory.getDriver();
    }

    /**
     * @param scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Driver closed for scenario : " + scenario.getName());
        SeleniumDriverFactory.quitDriver();
    }
}
