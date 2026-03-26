package org.selenium.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.selenium.page.BasePage;
import org.selenium.utils.SeleniumDriverFactory;
import org.testng.Assert;

public class CommonStepDefinitions {

    BasePage basePage = new BasePage(SeleniumDriverFactory.getDriver());

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        basePage.navigateTo("https://www.saucedemo.com");
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        basePage.login(username, password);
    }

    @Then("the user should see the inventory page")
    public void the_user_should_see_the_inventory_page() {
        Assert.assertTrue(basePage.isInventoryPageDisplayed(), "Inventory page not displayed");
    }

    @Then("the user should see the error message {string}")
    public void the_user_should_see_the_error_message(String expectedMessage) {
        String actualMessage = basePage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Error message does not match");
    }
}
