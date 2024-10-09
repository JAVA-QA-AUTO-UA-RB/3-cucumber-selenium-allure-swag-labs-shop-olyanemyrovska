package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BurgerMenu extends BasePage {
    private static final By bmItems = By.cssSelector(".bm-item-list");
    private static final By logoutLink = By.id("logout_sidebar_link");

    public BurgerMenu(WebDriver driver) {
        super(driver);
        waitForElementToBeVisible(bmItems);
    }

    public void logout() {
        waitForElementToBeVisible(logoutLink).click();
    }
}
