@Registration
Feature: Test Registration

  Background:
    Given <actor> go to bml login page
    When <actor> clicks on the user icon
    And <actor> clicks on the Register link

  @TC_REG_01
  Scenario Outline: TC_REG_01 Customer registers an account successfully and can log in
    When the customer enters registration info with name "<name>", phone "<phone>", email "<email>", password "<password>", confirm password "<confirmPassword>"
    And the customer clicks the "Đăng ký" button
    And the customer clicks "OK" on the success alert
    And the customer enters the registered email "<email>"
    And the customer enters the registered password "<password>"
    And the customer clicks the "Đăng nhập" button
    Then the system should display the customer's profile name "<name>"

    Examples:
      | name      | phone      | email                 | password | confirmPassword |
      | Phạm Hiền | 0382430037 | hienphamthi@gmail.com | 12345678 | 12345678        |

  @TC_REG_02_to_09
  Scenario Outline: <case> Customer registers with invalid or existing data
    When the customer enters registration info with name "<name>", phone "<phone>", email "<email>", password "<password>", confirm password "<confirmPassword>"
    And the customer clicks the "Đăng ký" button
    Then the system should display "<errorType>" for registration field "<field>" with message "<message>"

    Examples:
      | case      | name      | phone      | email              | password | confirmPassword | field             | errorType | message                                                                  |
      | TC_REG_02 |           | 0382430034 | hienbeou@gmail.com | 12345678 | 12345678        | Họ và tên         | tooltip   | Vui lòng điền vào trường này.                                            |
      | TC_REG_03 | Phạm Hiền |            | hienbeou@gmail.com | 12345678 | 12345678        | Số điện thoại     | alert     | Số điện thoại phải bắt đầu bằng số 0 và có độ dài chính xác là 10 chữ số |
      | TC_REG_04 | Phạm Hiền | 0382430034 |                    | 12345678 | 12345678        | Email             | tooltip   | Vui lòng điền vào trường này.                                            |
      | TC_REG_05 | Phạm Hiền | 0382430034 | hienbeou@gmail.com |          | 12345678        | Mật khẩu          | tooltip   | Vui lòng điền vào trường này.                                            |
      | TC_REG_06 | Phạm Hiền | 0382430034 | hienbeou@gmail.com | 12345678 |                 | Xác nhận mật khẩu | tooltip   | Vui lòng điền vào trường này.                                            |
      | TC_REG_07 |           |            |                    |          |                 | Họ và tên         | tooltip   | Vui lòng điền vào trường này.                                            |
      | TC_REG_08 | Phạm Hiền | 0352430036 | hienbeou@gmail.com | 12345678 | 12345678        | Số điện thoại     | alert     | Số điện thoại đã được sử dụng                                            |
      | TC_REG_09 | Phạm Hiền | 0352430036 | phuc.to@gmail.com  | 12345678 | 12345678        | Email             | alert     | Email đã được sử dụng                                                    |
