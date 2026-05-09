@ForgotPassWord
Feature: Test ForgotPassWord

  Background:
    Given <actor> go to bml login page
    When <actor> clicks on the user icon
    And <actor> clicks on the forgot password link

  @TC_FP_02 @ForgotPasswordSuccess
  Scenario Outline: TC_FP_02 - Đổi mật khẩu thành công và đăng nhập được với mật khẩu mới
    When <actor> resets password with phone "<phone>", email "<email>", new password "<newPassword>" and confirm password "<confirmPassword>"
    Then the user should see alert message "<resetMessage>"
    When the user accepts the alert
    And the user clicks the login link on forgot password page
    And the user logs in with username "<email>" and password "<newPassword>"
    Then the user should see the profile name as "<profileName>"
    Examples:
      | actor | phone      | email             | newPassword | confirmPassword | resetMessage                                          | profileName |
      | user  | 0902222003 | mai.le@nhaspa.com | 123456789   | 123456789       | Mật khẩu đã được thay đổi thành công. Vui lòng đăng nhập lại. | Lê Thị Mai  |


