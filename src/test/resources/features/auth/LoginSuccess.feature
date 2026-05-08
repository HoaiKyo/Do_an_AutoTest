@LoginSuccess
Feature: Test Logi Success and Account Management

  Background:
    Given <actor> go to bml login page

  @TC_LOG_03 @LoginSuccess
  Scenario Outline: TC_LOG_03 Login success with role user
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see the profile name as "<profileName>"
    Examples:
      | actor | username          | password | profileName |
      | user  | phuc.to@gmail.com | 12345678 | Tô Văn Phúc |


  @TC_LOG_04 @LoginSuccess
  Scenario Outline: TC_LOG_04 Login success with role letan
    When <actor> logs in with username "<username>" and password "<password>"
    Then the user should see the profile name as "<profileName>"
    When the user navigates to "Receptionist Management"
    Then the user should see the receptionist management page

    Examples:
      | actor | username         | password  | profileName |
      | letan | letan@nhaspa.com | 123456789 | Lễ Tân 1    |

  @TC_LOG_05 @LoginSuccess
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
    When  the user activates account "test@gmail.com" in Admin page
    And   the user logs out from Admin page

    # Phase 2: Activated user logs in successfully
    When  TestUser logs in with username "test@gmail.com" and password "12345678"
    Then  TestUser should see the profile name as "TestdeActive"

    Examples:
      | actor |
      | Admin |

