package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    public static final String LOGIN_PAGE_URI = "https://www.saucedemo.com/";
    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    private static final By loginBox = By.cssSelector(".login-box");
    private static final By usernameField = By.xpath("//input[@placeholder='Username']");
    private static final By passwordField = By.xpath("//input[@placeholder='Password']");
    private static final By loginBtn = By.id("login-button");
    private static final By errorMessage = By.xpath("//h3[@data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(LOGIN_PAGE_URI);
        waitForElementToBeVisible(loginBox);
    }

    public LoginPage enterUsername(String name) {
        clearAndSendKeys(usernameField, name);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clearAndSendKeys(passwordField, password);
        return this;
    }

    public void clickLoginBtn() {
        waitForElementToBeClickable(loginBtn).click();
    }

    public void submitLoginForm(String username, String password) {
        this.enterUsername(username)
                .enterPassword(password)
                .clickLoginBtn();
    }

    public ProductsPage submitLoginFormWithValidCredentials(String username, String password) {
        submitLoginForm(username, password);
        return new ProductsPage(driver);
    }

    public ProductsPage loginWithValidCredentials() {
        return submitLoginFormWithValidCredentials(VALID_USERNAME, VALID_PASSWORD);
    }

    public boolean isLoginPageDisplayed() {
        return waitForElementToBeVisible(loginBox).isDisplayed() &&
                waitForElementToBeVisible(usernameField).isDisplayed() &&
                waitForElementToBeVisible(passwordField).isDisplayed();
    }

    public String getErrorMessage() {
        WebElement errorElement = waitForElementToBeVisible(errorMessage);
        if (errorElement != null) {
            return errorElement.getText();
        } else {
            return "";
        }
    }
}