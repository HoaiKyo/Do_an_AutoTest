@LoginFailed
Feature: Test Login Failed and Account Management

  Background:
    Given <actor> go to bml login page

  @TC_LOG_07
  Scenario Outline: TC_LOG_07 Login failed with locked account
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see alert message "<message>" at the top of the screen
    Examples:
      | actor | username      | password | message              |
      | user  | abc@gmail.com | 12345678 | Tài khoản đã bị khóa |

  @TC_LOG_08 @TC_LOG_09 @TC_LOG_10 @EmptyField
  Scenario Outline:TC_LOG_08 TC_LOG_09 TC_LOG_10 - <caseName>
    When <actor> logs in with username "<username>" and password "<password>"
    Then <actor> should see required error tooltip for "<field>" with message "<message>"

    Examples:
      | caseName                    | actor | username      | password | field    | message                       |
      | Empty email, valid password | user  |               | 12345678 | Email    | Vui lòng điền vào trường này. |
      | Valid email, empty password | user  | abc@gmail.com |          | Mật khẩu | Vui lòng điền vào trường này. |
      | Empty email and password    | user  |               |          | Email    | Vui lòng điền vào trường này. |

  @TC_LOG_11 @LoginFail @PasswordWithTrailingSpace
  Scenario Outline: TC_LOG_11 - Login fails when password contains trailing spaces
    When <actor> logs in with username "<username>" and password "<password>"
    Then <actor> should see alert message "<message>"

    Examples:
      | actor | username      | password        | message                        |
      | user  | abc@gmail.com | 12345678[space] | Email hoặc mật khẩu không đúng |

  @TC_LOG_12 @TC_LOG_13 @TC_LOG_14 @LoginFail
  Scenario Outline: TC_LOG_12 TC_LOG_13 TC_LOG_14 - Login fails with invalid credentials - <caseName>
    When <actor> logs in with username "<username>" and password "<password>"
    Then <actor> should see alert message "<message>"

    Examples:
      | caseName                       | actor | username          | password  | message                        |
      | Account does not exist         | user  | phuc@gmail.com    | 666666    | Email hoặc mật khẩu không đúng |
      | Existing email, wrong password | user  | phuc.to@gmail.com | 123456789 | Email hoặc mật khẩu không đúng |
      | Wrong email, valid password    | user  | hoaikyo@gmail.com | 12345678  | Email hoặc mật khẩu không đúng |

  @TC_LOG_15 @LoginFail
  Scenario Outline: TC_LOG_15 - Login fails when email format is invalid
    When <actor> logs in with username "<username>" and password "<password>"
    Then <actor> should see required error tooltip for "<field>" with message "<message>"

    Examples:
      | actor | username | password | field | message                                                            |
      | user  | hoaimabu | 12345678 | Email | Vui lòng bao gồm '@' trong địa chỉ email. 'hoaimabu' bị thiếu '@'. |

  @TC_LOG_16 @LoginFail
  Scenario Outline: TC_LOG_16 - Login fails when password is shorter than minimum length
    When <actor> logs in with username "<username>" and password "<password>"
    Then <actor> should see alert message "<message>"

    Examples:
      | actor | username           | password | message            |
      | user  | hoaimabu@gmail.com | 123      | Đăng nhập thất bại |