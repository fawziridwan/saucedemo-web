package org.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.utils.YamlReader;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Locators moved to BasePage
    private final By usernameField = YamlReader.getLocator("login.username");
    private final By passwordField = YamlReader.getLocator("login.password");
    private final By loginButton = YamlReader.getLocator("login.login_button");
    private final By errorMessage = YamlReader.getLocator("login.error_message");
    private final By inventoryTitle = YamlReader.getLocator("inventory.title");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(By locator) {
        find(locator).click();
    }

    protected void type(By locator, String text) {
        find(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }

    // Consolidated methods
    public void navigateTo(String url) {
        driver.get(url);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isInventoryPageDisplayed() {
        return find(inventoryTitle).isDisplayed();
    }
}
