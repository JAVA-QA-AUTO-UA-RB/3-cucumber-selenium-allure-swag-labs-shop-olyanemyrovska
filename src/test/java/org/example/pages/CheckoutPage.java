package org.example.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    public static final String CHECKOUT_PAGE_ONE_URI = "https://www.saucedemo.com/checkout-step-one.html";
    public static final String CHECKOUT_PAGE_TWO_URI = "https://www.saucedemo.com/checkout-step-two.html";

    private static final By firstNameField = By.id("first-name");
    private static final By lastNameField = By.id("last-name");
    private static final By postalCodeField = By.id("postal-code");
    private static final By continueBtn = By.id("continue");
    private static final By errorMessage = By.xpath("//h3[@data-test = 'error']");
    private static final By finishBtn = By.id("finish");
    private static final By itemsSummaryPrice = By.className("summary_subtotal_label");


    public CheckoutPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad(CHECKOUT_PAGE_ONE_URI);
    }

    public CheckoutPage enterFirstName(String firstName) {
        clearAndSendKeys(firstNameField, firstName);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        clearAndSendKeys(lastNameField, lastName);
        return this;
    }

    public CheckoutPage enterPostalCode(String postalCode) {
        clearAndSendKeys(postalCodeField, postalCode);
        return this;
    }

    public void clickContinueBtn() {
        waitForElementToBeClickable(continueBtn).click();
    }

    public CheckoutPage fillInTheFormWithFakeData() {
        Faker faker = new Faker();
        enterFirstName(faker.name().firstName());
        enterLastName(faker.name().lastName());
        enterPostalCode(faker.address().zipCode());
        return this;
    }

    public OrderConfirmationPage clickFinishBtn() {
        waitForElementToBeClickable(finishBtn).click();
        return new OrderConfirmationPage(driver);
    }

    public String getErrorMessageText() {
        return waitForElementToBeVisible(errorMessage).getText();
    }

    public double getItemsTotalPrice() {
        waitForPageToLoad(CHECKOUT_PAGE_TWO_URI);
        String totalPriceStr = waitForElementToBeVisible(itemsSummaryPrice).getText();
        String price = totalPriceStr.replaceAll(".*\\$([0-9]+\\.[0-9]{2}).*", "$1").trim();
        return Double.parseDouble(price);
    }
}
