@CategoryManagement
Feature: Test Category Management Admin

  Background:
    Given <actor> go to bml login page
    When <actor> logs in with username "admin@nhaspa.com" and password "12345678"
    When the user navigates to "Admin Management"
    And the user click "Danh Mục"

