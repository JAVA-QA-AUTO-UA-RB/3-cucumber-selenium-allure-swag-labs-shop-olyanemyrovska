package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {
    public static final String CONFIRMATION_PAGE_URI = "https://www.saucedemo.com/checkout-complete.html";

    private static final By completeHeader = By.className("complete-header");
    private static final By completeText = By.className("complete-text");
    private static final By backHomeBtn = By.id("back-to-products");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad(CONFIRMATION_PAGE_URI);
    }

    public String getConfirmationPageHeaderTxt() {
        return waitForElementToBeVisible(completeHeader).getText();
    }

    public String getConfirmationPageText() {
        return waitForElementToBeVisible(completeText).getText();
    }

    public boolean isBackHomeBtnVisible() {
        return waitForElementToBeVisible(backHomeBtn).isDisplayed();
    }
}
