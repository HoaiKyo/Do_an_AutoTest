@LoginFailed
Feature: Test Logi Failed and Account Management

  Background:
    Given <actor> go to bml login page

  @TC_LOG_07
  Scenario Outline: TC_LOG_07 Login failed with locked account
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see alert message "<message>" at the top of the screen
    Examples:
      | actor | username      | password | message               |
      | user  | abc@gmail.com | 12345678 | Tài khoản đã bị khóa |