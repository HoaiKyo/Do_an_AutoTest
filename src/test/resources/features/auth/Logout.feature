@Logout
Feature: Test Logout functionality for different roles

  @TC_LOGOUT_01 @TC_LOGOUT_02 @TC_LOGOUT_03
  Scenario Outline: TC_LOGOUT_01 - Đăng xuất thành công với vai trò <role>
    Given user go to bml login page
    When user logs in with username "<username>" and password "<password>"
    Then the user should see the profile name as "<profileName>"
    And the user click Open Profile Menu
    And the user click option "Đăng Xuất"
    Then the user should see the profile name as "<profileNameLogout>"

    Examples:
      | role  | username          | password  | profileName    | profileNameLogout |
      | user  | phuc.to@gmail.com | 12345678  | Tô Văn Phúc    |                   |
      | letan | letan@nhaspa.com  | 123456789 | Lễ Tân 1       |                   |
      | admin | admin@nhaspa.com  | 12345678  | Admin Hệ Thống |                   |

    @TC_LOGOUT_04
    Scenario: TC_LOGOUT_04 - Lễ tân Đăng xuất thành công khi đang ở Trang Quản trị Lễ Tân
      Given user go to bml login page
      When user logs in with username "letan@nhaspa.com" and password "123456789"
      Then the user should see the profile name as "Lễ Tân 1"
      And the user click Open Profile Menu
      When the user navigates to "Receptionist Management"
      And the user click button Logout
      Then the user should see the profile name as ""

      @TC_LOGOUT_05
      Scenario: TC_LOGOUT_05 - Admin Đăng xuất thành công khi đang ở Trang Quản trị Admin
        Given user go to bml login page
        When user logs in with username "admin@nhaspa.com " and password "12345678"
        Then the user should see the profile name as "Admin Hệ Thống"
        And the user click Open Profile Menu
        When the user navigates to "Admin Management"
        And the user click button Logout
        Then the user should see the profile name as ""


