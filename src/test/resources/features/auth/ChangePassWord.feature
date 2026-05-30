@ChangePassWord
Feature: Test ChangePassWord

  @TC_CP_01
  Scenario Outline: TC_CP_01 Customer changes password successfully
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    And the customer clicks button with text "Đổi mật khẩu"
    And the customer enters current password "<current_password>"
    And the customer enters new password "<new_password>"
    And the customer enters confirm password "<confirm_password>"
    And the customer clicks the  button "Lưu thay đổi"
    Then the system displays the alert message "Đổi mật khẩu thành công!"

    Examples:
      | current_password | new_password | confirm_password |
      | 12345678         | 123456789    | 123456789        |

  @TC_CP_02
  Scenario Outline: TC_CP_02 Customer changes password successfully and can log in with the new password
    When <actor> go to bml login page
    And <actor> logs in with username "<email>" and password "<current_password>"
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    And the customer clicks button with text "Đổi mật khẩu"
    And the customer enters current password "<current_password>"
    And the customer enters new password "<new_password>"
    And the customer enters confirm password "<confirm_password>"
    And the customer clicks the  button "Lưu thay đổi"
    Then the system displays the alert message "Đổi mật khẩu thành công!"

    And the customer clicks the Profile icon
    And the user click option "Đăng Xuất"
    And the user logs in with username "<email>" and password "<new_password>"
    Then the user should see the profile name as "<profileName>"

    Examples:
     |actor | email             | current_password | new_password | confirm_password | profileName |
     |user | phuc.to@gmail.com | 123456789        | 12345679     | 12345679         | Tô Văn Phúc |

  @TC_CP_03
  Scenario: TC_CP_03 Customer cannot log in with old password after changing password successfully
    When <actor> go to bml login page
    And <actor> logs in with username "<email>" and password "<current_password>"
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    And the customer clicks button with text "Đổi mật khẩu"
    And the customer enters current password "<current_password>"
    And the customer enters new password "<new_password>"
    And the customer enters confirm password "<confirm_password>"
    And the customer clicks the  button "Lưu thay đổi"
    Then the system displays the alert message "Đổi mật khẩu thành công!"

    And the customer clicks the Profile icon
    And the user click option "Đăng Xuất"
    And the user logs in with username "<email>" and password "<current_password>"
    Then the system displays the alert message "Email hoặc mật khẩu không đúng"

    Examples:
      |actor | email             | current_password | new_password | confirm_password |
      |user | phuc.to@gmail.com | 12345679        | 12345678   | 12345678         |

  @TC_CP_04_05_06_07
  Scenario Outline: TC_CP_04_05_06_07 Customer changes password with empty fields
    When "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    And the customer clicks button with text "Đổi mật khẩu"
    And the customer enters current password "<current_password>"
    And the customer enters new password "<new_password>"
    And the customer enters confirm password "<confirm_password>"
    And the customer clicks the  button "Lưu thay đổi"
    Then the customer should see required error tooltip for "<empty_field>" with message "Vui lòng điền vào trường này."

    Examples:
      | current_password | new_password | confirm_password | empty_field       |
      |                  | 123456789    | 123456789        | Mật khẩu hiện tại |
      | 12345678         |              | 123456789        | Mật khẩu mới      |
      | 12345678         | 123456789    |                  | Xác nhận mật khẩu |
      |                  |              |                  | Mật khẩu hiện tại |

  @TC_CP_08_09
  Scenario Outline: TC_CP_08_09 Customer changes password with invalid data
    When "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    And the customer clicks button with text "Đổi mật khẩu"
    And the customer enters current password "<current_password>"
    And the customer enters new password "<new_password>"
    And the customer enters confirm password "<confirm_password>"
    And the customer clicks the  button "Lưu thay đổi"
    Then the system displays the alert message "<alert_message>"

    Examples:
      | current_password | new_password | confirm_password | alert_message                  |
      | 12345679         | 12345678     | 12345678         | Mật khẩu hiện tại không đúng   |
      | 12345678         | 12345678     | 123456789        | Mật khẩu xác nhận không khớp   |