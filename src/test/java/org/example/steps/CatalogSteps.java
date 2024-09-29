package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.dto.Product;
import org.example.helpers.SortingType;
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogSteps {
    private ProductsPage productsPage;
    private WebDriver driver;

    @Before
    public void setUp() throws InterruptedException {
        driver = helpers.WebDriverProvider.getDriver();
    }

    @Given("Authorized user is on the Products page")
    public void authorisedUserIsOnTheProductsPage() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.loginWithValidCredentials();
    }

    @When("User selects to sort products alphabetically")
    public void userSelectsToSortProductsAlphabetically() {
        productsPage.sortProduct(SortingType.NAME_A_TO_Z);
    }

    @Then("Products are sorted alphabetically")
    public void productsAreSortedAlphabetically() {
        List<String> actualProductNames = productsPage.getProducts().stream().map(Product::getName).collect(Collectors.toList());
        List<String> sortedProductNames = new ArrayList<>(actualProductNames);
        Collections.sort(sortedProductNames);
        Assertions.assertEquals(sortedProductNames, actualProductNames);
    }

    @When("User clicks on a product {string}")
    public void userClicksOnAProduct(String productName) {
        productsPage.clickProductDetailsLink(productName);
    }

    @Then("The product details section should be displayed for {string}")
    public void theProductDetailsSectionShouldBeDisplayed(String productName) {
        Assertions.assertNotNull(driver.getCurrentUrl());
        Assertions.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/inventory-item"));
        Assertions.assertNotNull(productsPage.getInventoryProductImage(), "Product image should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductTitle(), "Product title should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductDesc(), "Product description should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductPrice(), "Product price should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductCartButton(), "Add to cart button should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductDesc(), "Product description should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductPrice(), "Product price should be displayed");
        Assertions.assertNotNull(productsPage.getInventoryProductCartButton(), "Add to cart button should be displayed");
        Assertions.assertEquals(productsPage.getInventoryProductTitle().getText(), productName, "Product title does not match");

    }

    @After
    public void quitDriver() {
        helpers.WebDriverProvider.quitDriver();
    }
}
