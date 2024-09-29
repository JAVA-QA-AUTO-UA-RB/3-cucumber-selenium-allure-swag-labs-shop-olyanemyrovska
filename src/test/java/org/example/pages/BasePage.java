package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected WebDriver driver;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement waitForElementToBeVisible(By locator) {
        try {
            return new WebDriverWait(driver, TIMEOUT)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            //Suggested by GPT
            System.out.println("Element not found or not visible: " + locator);
            return null;
        }
    }

    protected List<WebElement> waitForElementsToBeVisible(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForPageToLoad(String url) {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.urlToBe(url));
    }

    public void clearAndSendKeys(By locator, String value){
        WebElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(value);
    }
}