package org.example.pages;

import org.example.dto.Product;
import org.example.helpers.SortingType;
import org.example.helpers.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {
    public static final String PRODUCTS_PAGE_URI = "https://www.saucedemo.com/inventory.html";
    public static final By burgerMenuBtn = By.id("react-burger-menu-btn");
    public static final By sortingMenu = By.cssSelector(".product_sort_container");
    public static final By shoppingCartLink = By.cssSelector(".shopping_cart_link");
    public static final By productsTitle = By.cssSelector(".title");

    public static final By productElements = By.className("inventory_item");
    public static final By productName = By.className("inventory_item_name");
    public static final By productPrice = By.className("inventory_item_price");
    public static final By productDescription = By.className("inventory_item_desc");
    public static final By inventoryDetailsImg = By.className("inventory_details_img");
    public static final By inventoryProductTitle = By.className("inventory_details_name");
    public static final By inventoryProductDesc = By.className("inventory_details_desc");
    public static final By inventoryProductPrice = By.className("inventory_details_price");
    public static final By inventoryProductAddToCartBtn = By.id("add-to-cart");


    public ProductsPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad(PRODUCTS_PAGE_URI);
    }

    public String getPageTitle() {
        return waitForElementToBeVisible(productsTitle).getText();
    }

    public BurgerMenu expandBurgerMenu() {
        waitForElementToBeClickable(burgerMenuBtn).click();
        return new BurgerMenu(driver);
    }

    public void sortProduct(SortingType sortingOption) {
        Select select = new Select(waitForElementToBeClickable(sortingMenu));
        select.selectByValue(sortingOption.getValue());
    }

    public CartPage navigateToCartPage() {
        waitForElementToBeClickable(shoppingCartLink).click();
        return new CartPage(driver);
    }

    public void addItemsToTheCart(int itemsCount) {
        List<Product> products = getProducts();
        if (itemsCount == 0 || itemsCount > products.size()) {
            throw new IllegalArgumentException(String.format("Cannot add to cart %s products, because only %s available", itemsCount, products.size()));
        }
        for (int i = 0; i < itemsCount; i++) {
            this.addToCart(products.get(i).getName());
        }
    }

    public List<Product> getProducts() {
        List<WebElement> products = waitForElementsToBeVisible(productElements);
        return products.stream().map(productElement -> {
            String name = productElement.findElement(productName).getText();
            String price = productElement.findElement(productPrice).getText();
            String description = productElement.findElement(productDescription).getText();
            return new Product(name, price, description);
        }).collect(Collectors.toList());
    }

    public void addToCart(String productName) {
        waitForElementToBeClickable(getAddToCartProductLocator(productName)).click();
    }

    public void clickProductDetailsLink(String productName) {
        String productNameLocator = "//div[text()='" + productName + "']";
        waitForElementToBeClickable(By.xpath(productNameLocator)).click();
    }

    public By getAddToCartProductLocator(String productName) {
        String addToCartLocator = "add-to-cart-".concat(StringUtils.formatProductNameWithHyphens(productName));
        return By.id(addToCartLocator);
    }

    public WebElement getInventoryProductImage() {
        return waitForElementToBeVisible(inventoryDetailsImg);
    }

    public WebElement getInventoryProductTitle() {
        return waitForElementToBeVisible(inventoryProductTitle);
    }

    public WebElement getInventoryProductDesc() {
        return waitForElementToBeVisible(inventoryProductDesc);
    }

    public WebElement getInventoryProductCartButton() {
        return waitForElementToBeVisible(inventoryProductAddToCartBtn);
    }

    public WebElement getInventoryProductPrice() {
        return waitForElementToBeVisible(inventoryProductPrice);
    }
}