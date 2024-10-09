package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverProvider {
    private static WebDriver driver;

    // Private constructor to prevent instantiation
    private WebDriverProvider() {
    }

    // Get the WebDriver instance
    public static WebDriver getDriver() throws InterruptedException {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
