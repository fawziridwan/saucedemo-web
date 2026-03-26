@login
Feature: Login functionality for SauceDemo

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the user should see the inventory page

  Scenario Outline: Failed login with invalid or locked out credentials
    Given the user is on the login page
    When the user logs in with username "<username>" and password "<password>"
    Then the user should see the error message "<error_message>"

    Examples:
      | username        | password       | error_message                                               |
      | wrong_user      | wrong_password | Epic sadface: Username and password do not match any user   |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.        |
