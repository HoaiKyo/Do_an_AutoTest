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
    Given "admin" go to bml login page
    And "admin" logs in with customer credentials from config
