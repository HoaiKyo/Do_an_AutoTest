@AppointmentCancel
Feature: Test Appointment Cancel

  @TC_APT_CANCEL_01
  Scenario: TC_APT_CANCEL_01 Customer cancels an appointment successfully
    Given "customer" go to bml login page
    When "customer" logs in with customer credentials from config
    And the customer clicks the Profile icon
    And the customer clicks "Personal Profile"
    And the customer clicks the "Hủy lịch" button
    And the customer clicks "OK" on the confirmation alert
    Then the system displays the alert message "Đã hủy lịch hẹn thành công"

  @TC_APT_CANCEL_02 @receptionistCancel
  Scenario: TC_APT_CANCEL_02 Receptionist cancels an appointment successfully
    Given "letan" go to bml login page
    When "letan" logs in with letan credentials from config
    And the user navigates to "Receptionist Management"
    And the receptionist clicks the "Lịch hẹn" menu item
    And the receptionist clicks the eye icon to view appointment details
    And the receptionist clicks the "Hủy" button
    And the admin enters cancellation reason "Chờ xác nhận"
    And the admin clicks the "Xác nhận hủy" button
    Then the system displays the alert message "Hủy lịch hẹn thành công"

  @TC_APT_CANCEL_03 @adminCancel
  Scenario: TC_APT_CANCEL_03 Admin cancels an appointment successfully
    Given "admin" go to bml login page
    When "admin" logs in with admin credentials from config
    And the user navigates to "Admin Management"
    And the admin clicks the "Lịch Hẹn" menu item
    And the admin clicks the eye icon in the action column to view appointment details
    And the admin clicks the "Hủy" button
    And the admin enters cancellation reason "Chờ xác nhận"
    And the admin clicks the "Xác nhận hủy" button
    Then the system displays the alert message "Hủy lịch hẹn thành công"

  @TC_APT_CANCEL_04 @receptionistCancelValidation
  Scenario: TC_APT_CANCEL_04 Receptionist cannot cancel an appointment without entering cancellation reason
    Given "letan" go to bml login page
    When "letan" logs in with letan credentials from config
    And the user navigates to "Receptionist Management"
    And the receptionist clicks the "Lịch hẹn" menu item
    And the receptionist clicks the eye icon to view appointment details
    And the receptionist clicks the "Hủy" button
    And the admin clicks the "Xác nhận hủy" button
    Then the system displays the alert message "Vui lòng nhập lý do hủy"

  @TC_APT_CANCEL_05 @adminCancelValidation
  Scenario: TC_APT_CANCEL_05 Admin cannot cancel an appointment without entering cancellation reason
    Given "admin" go to bml login page
    When "admin" logs in with admin credentials from config
    And the user navigates to "Admin Management"
    And the admin clicks the "Lịch Hẹn" menu item
    And the admin clicks the eye icon in the action column to view appointment details
    And the admin clicks the "Hủy" button
    And the admin clicks the "Xác nhận hủy" button
    Then the system displays the alert message "Vui lòng nhập lý do hủy"



