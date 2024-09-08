[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/ltK0PwXf)
# Завдання: Автоматизація тестування інтернет-магазину Sauce Demo

**Ціль:** Написати автоматизовані тест-кейси для перевірки ключових функціональностей сайту Sauce Demo, використовуючи Selenium WebDriver, TestNG, Page Object Pattern, Cucumber для BDD сценаріїв, та Allure для звітності.

**Сайт для тестування:** [Sauce Demo](https://www.saucedemo.com/) — тренувальний інтернет-магазин з товарами, кошиком та оформленням замовлення.

## Інструкції:

1. **Налаштування проекту:**
    - Вам надається шаблон проєкту, але залежності до ного додані лише частково - вам потрібно буде додати залежності, яких не вистачає
    - Додайте залежності для Selenium WebDriver, і Allure у файл `pom.xml`.
    - Для нормальної інтеграції усих перелічених інструментів у ваш проєкт вам можливо доведеться почитати документацію до Cucumber і Allure
    - Очікується, що можна буде виконувати .feature файли з виконанням команди `mvn test` або `mvn verify` якщо треба згенерувати Allure report

2. **Step Definitions (Cucumber):**
    - Напишіть Gherkin-сценарії для перевірки основних функцій сайту:
        - Авторизація користувача (успішна та неуспішна).
        - Додавання товарів до кошика.
        - Видалення товарів з кошика.
        - Оформлення замовлення.

3. **Page Object Model (POM):**
   - Створіть Page Objects для ключових сторінок:
        - `LoginPage`: методи для введення логіна, пароля та натискання кнопки "Login".
        - `ProductsPage`: методи для додавання товарів до кошика, сортування товарів, переходу до кошика.
        - `CartPage`: методи для перегляду товарів у кошику, видалення товарів, переходу до оформлення замовлення.
        - `CheckoutPage`: методи для заповнення інформації про покупця та завершення замовлення.
        - `OrderConfirmationPage`: метод для перевірки повідомлення про успішне замовлення.

4. **Приклади Cucumber-сценаріїв:**

   ```gherkin
   Feature: Shopping Cart Functionality
     Scenario: Add items to the cart
       Given the user is logged in with valid credentials
       When the user adds a "Sauce Labs Backpack" to the cart
       Then the item should be displayed in the cart

     Scenario: Checkout process
       Given the user has items in the cart
       When the user proceeds to checkout
       And enters valid customer information
       Then the order should be successfully placed

5. **Кількість і деталізація сценаріїв**
   - `.feature` файли вже створено в проєкті, вам потрібно дописати сценарії, які перевіряють відповідний функціонал веб-додатку
   - Кількість сценаріїв можна визначити на власний розсуд, але переконайтесь, що ви додали **як мінімум** один позитивний і один негативний сценарій до кожного `.feature` файлу

6. **Очікуваний результат сдачі Домашки**
   - Пул-реквест в напрямку `main` вашого персонального репозиторію
   - Файл `README-TEST.md` з описом як правильно запускати тести, а також прикладом виводу консолі при виконанні команди `mvn test` або `mvn verify`
   - Звісно ж зелені тести :)