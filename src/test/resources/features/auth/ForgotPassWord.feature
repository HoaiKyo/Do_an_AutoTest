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
      | actor | phone      | email             | newPassword | confirmPassword | resetMessage                                                  | profileName |
      | user  | 0902222003 | mai.le@nhaspa.com | 123456789   | 123456789       | Mật khẩu đã được thay đổi thành công. Vui lòng đăng nhập lại. | Lê Thị Mai  |

  @TC_FP_03 @ForgotPasswordSuccess
  Scenario Outline: TC_FP_03 - Đổi mật khẩu thành công và đăng nhập thất bại với pass cũ
    When <actor> resets password with phone "<phone>", email "<email>", new password "<newPassword>" and confirm password "<confirmPassword>"
    Then the user should see alert message "<resetMessage>"
    When the user accepts the alert
    And the user clicks the login link on forgot password page
    And the user logs in with username "<email>" and password "<oldPassword>"
    Then <actor> should see alert message "<message>"
    Examples:
      | actor | phone      | email             | newPassword | confirmPassword | resetMessage                                                  | oldPassword | message                        |
      | user  | 0902222003 | mai.le@nhaspa.com | 123456789   | 123456789       | Mật khẩu đã được thay đổi thành công. Vui lòng đăng nhập lại. | 12345678    | Email hoặc mật khẩu không đúng |

  @TC_FP_03 @TC_FP_04 @TC_FP_05 @TC_FP_06 @TC_FP_07 @EmptyFieldFP
  Scenario Outline: <testCase> - <caseName>
    When <actor> resets password with phone "<phone>", email "<email>", new password "<newPassword>" and confirm password "<confirmPassword>"
    Then <actor> should see required error tooltip for forgot password field "<field>" with message "<message>"

    Examples:
      | testCase | caseName                              | actor | phone      | email             | newPassword | confirmPassword | field                 | message                       |
      | TC_FP_03 | Bỏ trống trường số điện thoại         | user  |            | mai.le@nhaspa.com | 123456789   | 123456789       | Số điện thoại         | Vui lòng điền vào trường này. |
      | TC_FP_04 | Bỏ trống trường email                 | user  | 0902222003 |                   | 123456789   | 123456789       | Email                 | Vui lòng điền vào trường này. |
      | TC_FP_05 | Bỏ trống trường mật khẩu mới          | user  | 0902222003 | mai.le@nhaspa.com |             | 123456789       | Mật khẩu mới          | Vui lòng điền vào trường này. |
      | TC_FP_06 | Bỏ trống trường xác nhận mật khẩu mới | user  | 0902222003 | mai.le@nhaspa.com | 123456789   |                 | Xác nhận mật khẩu mới | Vui lòng điền vào trường này. |
      | TC_FP_07 | Bỏ trống tất cả trường nhập liệu      | user  |            |                   |             |                 | Số điện thoại         | Vui lòng điền vào trường này. |
