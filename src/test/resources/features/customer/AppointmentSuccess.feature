@AppointmentSuccess
Feature: Test Appointment Success

  @TC_APT_01
  Scenario: TC_APT_01 Customer books an appointment successfully and checks it in personal profile
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer selects appointment time "auto_time"
    And the customer clicks the "Next" button
    And the customer selects service "auto_service"
    And the customer selects specialist "auto_specialist"
    And the customer clicks the "Next" button
    And the customer enters full name "auto_name"
    And the customer enters phone number "auto_phone"
    And the customer clicks the "Book Appointment" button
    Then the system displays a successful appointment booking popup
    When the customer clicks the "Close" button on the popup
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    Then the system displays the newly created appointment in the history


  @TC_APT_02
  Scenario: TC_APT_02 Customer books an appointment successfully with 2 services and checks it in personal profile
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer selects appointment time "auto_time"
    And the customer clicks the "Next" button
    And the customer clicks the "Add Row" button
    And the customer selects service "auto_service"
    And the customer selects specialist "auto_specialist"
    And the customer selects service at row 2 "auto_service_2"
    And the customer selects available specialist at row 2 "auto_specialist_2"
    And the customer clicks the "Next" button
    And the customer enters full name "auto_name"
    And the customer enters phone number "auto_phone"
    And the customer clicks the "Book Appointment" button
    Then the system displays a successful appointment booking popup
    When the customer clicks the "Close" button on the popup
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    Then the system displays the newly created appointment in the history

  @TC_APT_03
  Scenario: TC_APT_03 Customer books an appointment successfully with companion information
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer selects appointment time "auto_time"
    And the customer enters companion quantity "1"
    And the customer enters companion name "auto_companion_name"
    And the customer clicks the "Next" button
    And the customer selects service "auto_service"
    And the customer selects specialist "auto_specialist"
    And the customer selects service at row 2 "auto_service_2"
    And the customer selects available specialist at row 2 "auto_specialist_2"
    And the customer clicks the "Next" button
    And the customer enters full name "auto_name"
    And the customer enters phone number "auto_phone"
    And the customer clicks the "Book Appointment" button
    Then the system displays a successful appointment booking popup
    When the customer clicks the "Close" button on the popup
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    Then the system displays the newly created appointment in the history

    @TC_APT_04
    Scenario: TC_APT_04 Admin creates an appointment successfully
      Given "admin" go to bml login page
      And "admin" logs in with admin credentials from config
      And the admin navigates to "Admin Management"
      When the admin clicks the "Lịch Hẹn" button
      And the admin clicks the "Tạo lịch hẹn" button
      And the admin enters customer full name "auto_name"
      And the admin enters customer phone number "auto_phone"
      And the admin enters appointment date "auto_date"
      And the admin selects start time "auto_time"
      And the admin selects service "auto_service"
      And the admin selects suitable specialist "auto_specialist" row one
      And the admin clicks the "Lưu lịch hẹn" button
      Then the system displays a successful appointment creation alert
      When the admin clicks "OK" on the success alert
      Then the system displays the newly created appointment in the appointment history list

  @TC_APT_05
  Scenario: TC_APT_05 Admin creates an appointment successfully with multiple services
    Given "admin" go to bml login page
    And "admin" logs in with admin credentials from config
    And the admin navigates to "Admin Management"
    When the admin clicks the "Lịch Hẹn" button
    And the admin clicks the "Tạo lịch hẹn" button
    And the admin enters customer full name "auto_name"
    And the admin enters customer phone number "auto_phone"
    And the admin enters appointment date "auto_date"
    And the admin selects start time "auto_time"
    And the admin selects service "auto_service"
    And the admin clicks the "Thêm dòng dịch vụ" button
    And the admin selects service "auto_service_2" for service row 2
    And the admin selects suitable specialist "auto_specialist"
    And the admin selects suitable specialist "auto_specialist_2" for service row 2
    And the admin clicks the "Lưu lịch hẹn" button
    Then the system displays a successful appointment creation alert
    When the admin clicks "OK" on the success alert
    Then the system displays the newly created appointment in the appointment history list

  @TC_APT_06
  Scenario: TC_APT_06 Admin creates an appointment successfully with companion information
    Given "admin" go to bml login page
    And "admin" logs in with admin credentials from config
    And the admin navigates to "Admin Management"
    When the admin clicks the "Lịch Hẹn" button
    And the admin clicks the "Tạo lịch hẹn" button
    And the admin enters customer full name "auto_name"
    And the admin enters customer phone number "auto_phone"
    And the admin enters appointment date "auto_date"
    And the admin selects start time "auto_time"
    And the admin clicks the "Thêm người" button
    And the admin enters companion name "auto_companion_name"
    And the admin selects service "auto_service" row one
    And the admin clicks the "Thêm dòng dịch vụ" button
    And the admin selects the "auto_companion_name" as the service user
    And the admin selects service "auto_service_2" for service row two
    And the admin selects suitable specialist "auto_specialist" row one
    And the admin selects suitable specialist "auto_specialist_2" for service row two
    And the admin clicks the "Lưu lịch hẹn" button
    Then the system displays a successful appointment creation alert
    When the admin clicks "OK" on the success alert
    Then the system displays the newly created appointment in the appointment history list

  @TC_APT_07
  Scenario: TC_APT_07 Customer leaves appointment time empty
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer clicks the "Next" button
    Then the system displays the alert message "Vui lòng chọn khung giờ hẹn."

    @TC_APT_08
    Scenario: TC_APT_08 Customer cannot book an appointment without selecting a service
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer selects appointment time "auto_time"
    And the customer clicks the "Next" button
    And the customer clicks the "Next" button
    Then the system displays the alert message "Vui lòng chọn ít nhất 1 dịch vụ."

  @TC_APT_09
  Scenario: TC_APT_09 Customer cannot book an appointment without entering full name
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer selects appointment time "auto_time"
    And the customer clicks the "Next" button
    And the customer selects service "auto_service"
    And the customer selects specialist "auto_specialist"
    And the customer clicks the "Next" button
    And the customer clicks the "Book Appointment" button
    Then the system displays the alert message "Vui lòng điền họ tên."

  @TC_APT_10
  Scenario: TC_APT_10 Customer cannot book an appointment without entering phone number
    Given "customer" go to bml login page
    And "customer" logs in with customer credentials from config
    When the customer clicks the "Book Now" button on the Banner
    And the customer selects appointment date "auto_date"
    And the customer selects appointment time "auto_time"
    And the customer clicks the "Next" button
    And the customer selects service "auto_service"
    And the customer selects specialist "auto_specialist"
    And the customer clicks the "Next" button
    And the customer enters full name "auto_name"
    And the customer clicks the "Book Appointment" button
    Then the system displays the alert message "Vui lòng điền số điện thoại."

  @TC_APT_11
  Scenario: TC_APT_11 Admin cannot create an appointment without selecting start time
    Given "admin" go to bml login page
    And "admin" logs in with admin credentials from config
    And the admin navigates to "Admin Management"
    When the admin clicks the "Lịch Hẹn" button
    And the admin clicks the "Tạo lịch hẹn" button
    And the admin enters appointment date "auto_date"
    And the admin clicks the "Lưu lịch hẹn" button
    Then the system displays the alert message "Khung giờ hẹn không hợp lệ. Vui lòng chọn lại slot 30 phút."

