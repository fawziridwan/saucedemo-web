package org.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.selenium.page.BasePage;
import org.selenium.utils.SeleniumDriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;
    private BasePage basePage;

    @BeforeMethod
    public void setUp() {
        driver = SeleniumDriverFactory.getDriver();
        basePage = new BasePage(driver);
        basePage.navigateTo("https://www.saucedemo.com");
    }

    @Test
    public void successfulLogin() {
        basePage.login("standard_user", "secret_sauce");
        Assert.assertTrue(basePage.isInventoryPageDisplayed(), "Inventory page is not displayed after successful login.");
    }

    @Test
    public void failedLoginWithWrongCredentials() {
        basePage.login("wrong_user", "wrong_password");
        String errorMessage = basePage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface: Username and password do not match any user in this service"), "Error message for wrong credentials is not correct.");
    }

    @Test
    public void failedLoginWithLockedOutUser() {
        basePage.login("locked_out_user", "secret_sauce");
        String errorMessage = basePage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface: Sorry, this user has been locked out."), "Error message for locked out user is not correct.");
    }

    @AfterMethod
    public void tearDown() {
        SeleniumDriverFactory.quitDriver();
    }
}
