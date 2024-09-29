Feature: Swag Labs User Login

  Scenario: Successful login with valid credentials
    Given User is on the login page
    When User logs in with valid username and password
    Then User is redirected to the product page

  Scenario Outline: Login with incorrect credentials
    Given User is on the login page
    When User logs in with username "<username>" and password "<password>"
    Then Error "<message>" is displayed

    Examples:
      | username        | password         | message                                                                   |
      | unknown_user    | secret_sauce     | Epic sadface: Username and password do not match any user in this service |
      | standard_user   | unknown_password | Epic sadface: Username and password do not match any user in this service |
      | locked_out_user | secret_sauce     | Epic sadface: Sorry, this user has been locked out.                       |

  Scenario Outline: Login with missing credentials
    Given User is on the login page
    When User logs in with username "<username>" and password "<password>"
    Then Error "<message>" is displayed

    Examples:
      | username      | password | message                            |
      |               |          | Epic sadface: Username is required |
      | standard_user |          | Epic sadface: Password is required |

  Scenario: User can successfully logout
    Given User is logged in with valid credentials
    When User clicks logout btn
    Then User is redirected to the Login page