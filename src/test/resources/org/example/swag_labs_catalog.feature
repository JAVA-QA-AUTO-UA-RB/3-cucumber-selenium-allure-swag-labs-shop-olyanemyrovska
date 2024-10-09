# Додайте сюди сценарії, які перевіряють сторінку зі списком товарів, а також сторінку з деталями товару
# Подумайте, які з них ви покрили б в першу чергу, що важливо користувачу інтернет-магазину? Що важливо власнику інтернет-магазину?
Feature: Products Page Functionality

  Scenario: User views product details on the catalog page
    Given Authorized user is on the Products page
    When User clicks on a product "Sauce Labs Backpack"
    Then The product details section should be displayed for "Sauce Labs Backpack"

  Scenario: Products can be sorted alphabetically
    Given Authorized user is on the Products page
    When User selects to sort products alphabetically
    Then Products are sorted alphabetically