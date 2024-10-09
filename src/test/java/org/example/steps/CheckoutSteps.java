package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.dto.CartItem;
import org.example.pages.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;


public class CheckoutSteps {
    private WebDriver driver;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage confirmationPage;
    private ProductsPage productsPage;
    private List<CartItem> removedCartItems;
    private List<CartItem> currentCartItems;

    @Before
    public void setUp() throws InterruptedException {
        driver = helpers.WebDriverProvider.getDriver();
    }

    @When("User adds product {string} to the cart")
    public void userAddsProductToTheCart(String product_name) {
        productsPage = new ProductsPage(driver);
        productsPage.addToCart(product_name);
    }


    @Given("User has {int} item(s) in the cart")
    public void userHasItemsInTheCart(int count) {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginWithValidCredentials();
        productsPage.addItemsToTheCart(count);
        cartPage = productsPage.navigateToCartPage();
        currentCartItems = cartPage.getCartItems();
        Assertions.assertEquals(currentCartItems.size(), count);
    }


    @When("User removes {int} items from the cart")
    public void userRemovesItemsFromTheCart(int count) {
        if (currentCartItems.size() > count) {
            removedCartItems = currentCartItems.stream().limit(count).collect(Collectors.toList());
            removedCartItems.forEach(itemToRemove -> cartPage.removeItemFromCart(itemToRemove));
        } else
            throw new IllegalArgumentException(String.format("Cannot remove %s items from the cart as only %s items present", count, currentCartItems.size()));
    }

    @Then("Removed item(s) are not displayed in the cart")
    public void removedItemsAreNotDisplayedInTheCart() {
        List<CartItem> cartItemsAfterRemoving = cartPage.getCartItems();
        Assertions.assertFalse(cartItemsAfterRemoving.containsAll(removedCartItems), "Removed items should not be displayed in the cart.");
    }

    @Given("User proceeds to checkout")
    public void userProceedsToCheckout() {
        checkoutPage = cartPage.proceedToCheckout();
    }

    @And("Enters valid customer information")
    public void entersValidCustomerInformation() {
        checkoutPage.fillInTheFormWithFakeData().clickContinueBtn();
    }

    @Then("Correct total products price is displayed")
    public void andCorrectTotalProductsPriceIsDisplayed() {
        double expectedTotalPrice = currentCartItems.stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        double actualTotalPrice = checkoutPage.getItemsTotalPrice();
        Assertions.assertEquals(expectedTotalPrice, actualTotalPrice);
    }

    @And("Clicks on the Finish button")
    public void clicksOnTheFinishButton() {
        confirmationPage = checkoutPage.clickFinishBtn();
    }

    @And("The order is successfully placed")
    public void theOrderIsSuccessfullyPlaced() {
        String successHeader = confirmationPage.getConfirmationPageHeaderTxt();
        String successMessage = confirmationPage.getConfirmationPageText();
        Assertions.assertEquals(successHeader, "Thank you for your order!");
        Assertions.assertEquals(successMessage, "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    @When("Enters invalid customer information {string} {string} {string}")
    public void entersInvalidCustomerInformation(String firstName, String lastName, String postcode) {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postcode)
                .clickContinueBtn();
    }

    @Then("Error message {string} is displayed")
    public void errorMessageIsDisplayed(String message) {
        String actualErrorMessage = checkoutPage.getErrorMessageText();
        Assertions.assertEquals(message, actualErrorMessage);
    }

    @Then("The item {string} with price {double} should be displayed in the cart")
    public void theItemWithPriceShouldBeDisplayedInTheCart(String name, double price) {
        cartPage = productsPage.navigateToCartPage();
        currentCartItems = cartPage.getCartItems();
        Assertions.assertEquals(currentCartItems.size(), 1, "One product should be present in the cart");

        CartItem actualCartItem = currentCartItems.get(0);
        Assertions.assertEquals(name, actualCartItem.getName(), "Product name does not match");
        Assertions.assertEquals(price, actualCartItem.getPrice(), "Product price does not match");
    }

    @After
    public void quitDriver() {
        helpers.WebDriverProvider.quitDriver();
    }
}
