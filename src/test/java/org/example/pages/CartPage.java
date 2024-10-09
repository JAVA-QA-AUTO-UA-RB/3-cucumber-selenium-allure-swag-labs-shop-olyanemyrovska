package org.example.pages;

import org.example.dto.CartItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.example.helpers.StringUtils.formatProductNameWithHyphens;

public class CartPage extends BasePage {

    public static final String CART_PAGE_URI = "https://www.saucedemo.com/cart.html";

    private final By pageTitle = By.xpath("//span[text()='Your Cart']");
    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemQuantity = By.className("cart_quantity");
    private final By itemPrice = By.className("inventory_item_price");
    private final By continueShoppingBtn = By.id("continue-shopping");
    private final By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad(CART_PAGE_URI);
        waitForElementToBeVisible(pageTitle);
    }

    public List<CartItem> getCartItems() {
        List<WebElement> cartElements = waitForElementsToBeVisible(cartItems);
        List<CartItem> cartItemsList = new ArrayList<>();
        for (WebElement cartElement : cartElements) {
            String name = cartElement.findElement(itemName).getText();
            int quantity = Integer.parseInt(cartElement.findElement(itemQuantity).getText());
            String priceText = cartElement.findElement(itemPrice).getText();
            double price = Double.parseDouble(priceText.replaceAll("[$]", "").trim()); // Remove dollar sign and trim

            CartItem cartItem = new CartItem(name, quantity, price);
            cartItemsList.add(cartItem);
        }
        return cartItemsList;
    }

    public void removeItemFromCart(CartItem item) {
        waitForElementToBeClickable(getRemoveCartItemLocator(item.getName())).click();
    }

    public CheckoutPage proceedToCheckout() {
        waitForElementToBeClickable(checkoutBtn).click();
        return new CheckoutPage(driver);
    }

    public void continueShopping() {
        waitForElementToBeClickable(continueShoppingBtn).click();
    }

    public By getRemoveCartItemLocator(String productName) {
        String removeBtnStrIdLocator = "remove-" + formatProductNameWithHyphens(productName);
        return By.id(removeBtnStrIdLocator);
    }
}

