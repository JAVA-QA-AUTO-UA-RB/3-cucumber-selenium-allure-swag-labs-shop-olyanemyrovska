# Додайте сюди сценарії які перевіряють так званий Checkout Flow, тобто процес оформлення замовлення
  # Вхідною точкою можна вважати момент, коли в корзину додано хоча б один товар, вихідна точка - оформлене замовлення
  # Приклади - можна купити один окремий товар, можна купити декілька товарів в одноиму замовленні
  # Можна додавати і видаляти товари з корзини
  # При успішному оформленні замовлення має відображатися повідомлення про успішне оформлення і т.п.
Feature: Shopping Cart Functionality

  Scenario Outline: Add items to the cart
    Given Authorized user is on the Products page
    When User adds product "<name>" to the cart
    Then The item "<name>" with price <price> should be displayed in the cart

    Examples:
      | name                    | price |
      | Sauce Labs Backpack     | 29.99 |
      | Sauce Labs Bolt T-Shirt | 15.99 |

  Scenario: Removing a product from the cart
    Given User has 5 items in the cart
    When User removes 2 items from the cart
    Then Removed items are not displayed in the cart

  Scenario: Total products price calculation
    Given User has 5 items in the cart
    When User proceeds to checkout
    And Enters valid customer information
    Then Correct total products price is displayed

  Scenario Outline: Success checkout flow
    Given User has <count> items in the cart
    When User proceeds to checkout
    And Enters valid customer information
    And Clicks on the Finish button
    Then The order is successfully placed

    Examples:
      | count |
      | 1     |
      | 5     |

  Scenario Outline: Failed checkout due to missing user information
    Given User has 1 items in the cart
    When User proceeds to checkout
    And Enters invalid customer information "<first_name>" "<last_name>" "<postal_code>"
    Then Error message "<error_message>" is displayed

    Examples:
      | first_name | last_name | postal_code | error_message                  |
      |            | User      | 123         | Error: First Name is required  |
      | Test       |           | 123         | Error: Last Name is required   |
      | Test       | User      |             | Error: Postal Code is required |