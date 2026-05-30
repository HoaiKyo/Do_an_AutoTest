@Login
Feature: Test Login

  Background:
    Given <actor> go to bml login page

  @TC_LOG_03
  Scenario Outline: TC_LOG_03 Login success with role user
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see the profile name as "<profileName>"
    Examples:
      | actor | username          | password | profileName |
      | user  | phuc.to@gmail.com | 12345678 | Tô Văn Phúc |

  @TC_LOG_04
  Scenario Outline: TC_LOG_04 Login success with role letan
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see the profile name as "<profileName>"
    When the user navigates to "Receptionist Management"
    Then the user should see the receptionist management page

    Examples:
      | actor | username         | password  | profileName |
      | letan | letan@nhaspa.com | 123456789 | Lễ Tân 1    |

  @TC_LOG_05
  Scenario Outline: TC_LOG_05 Login success with role admin
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see the profile name as "<profileName>"
    When the user navigates to "Admin Management"
    Then the user should see the admin management page

    Examples:
      | actor | username         | password  | profileName    |
      | admin | admin@nhaspa.com | 12345678  | Admin Hệ Thống |

  @TC_LOG_06 @LoginAccActivated
  Scenario Outline: TC_LOG_06 Admin activates user account and verifies login success
    # Phase 1: Admin performs activation
    Given Admin logs in with username "admin@nhaspa.com" and password "12345678"
    When  the user activates account "hienbeo@gmail.com" in Admin page
    And   the user logs out from Admin page

    # Phase 2: Activated user logs in successfully
    When  TestUser logs in with username "hienbeo@gmail.com" and password "12345678"
    Then  TestUser should see the profile name as "Phạm Hiền"

    Examples:
    |user|
    |admin|

  @TC_LOG_07
  Scenario Outline: TC_LOG_07 Login failed with locked account
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see alert message "<message>" at the top of the screen
    Examples:
      | actor | username      | password | message              |
      | user  | hien@gmail.com | 12345678 | Tài khoản đã bị khóa |

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
