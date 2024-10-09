
package org.example.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static org.example.pages.LoginPage.LOGIN_PAGE_URI;
import static org.example.pages.ProductsPage.PRODUCTS_PAGE_URI;

public class SwagLabsLoginSteps {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private WebDriver driver;

    @Before
    public void setUp() throws InterruptedException {
        driver = helpers.WebDriverProvider.getDriver();
    }

    @Given("User is on the login page")
    public void userIsOnTheLoginPage() {
        loginPage = new LoginPage(driver);
    }

    @When("User logs in with username {string} and password {string}")
    public void userLogsInWithUsernameAndPassword(String username, String password) {
        loginPage.submitLoginForm(username, password);
    }

    @When("User logs in with valid username and password")
    public void userLogsInWithValidUsernameAndPassword() {
        productsPage = loginPage.loginWithValidCredentials();
    }


    @Given("User is logged in with valid credentials")
    public void userIsLoggedInWithValidCredentials() {
        userIsOnTheLoginPage();
        userLogsInWithValidUsernameAndPassword();
    }

    @Then("User is redirected to the product page")
    public void userIsRedirectedToTheProductsPage() {
        Assertions.assertEquals(PRODUCTS_PAGE_URI, driver.getCurrentUrl());
        Assertions.assertEquals("Products", productsPage.getPageTitle());
    }

    @Then("Error {string} is displayed")
    public void errorIsDisplayed(String message) {
        Assertions.assertEquals(message, loginPage.getErrorMessage(), "Login error message does not match");
    }

    @When("User clicks logout btn")
    public void userClicksLogoutBtn() {
        productsPage.expandBurgerMenu().logout();
    }

    @Then("User is redirected to the Login page")
    public void userIsRedirectedToTheLoginPage() {
        Assertions.assertEquals(LOGIN_PAGE_URI, driver.getCurrentUrl());
        Assertions.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed after logout");
    }
}