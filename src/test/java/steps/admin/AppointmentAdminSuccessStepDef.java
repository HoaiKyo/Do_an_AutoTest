package steps.admin;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actors.OnStage;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.questions.admin.AlertSuccessIsVisible;
import screenplay.questions.admin.VerifyAdminAppointmentInHistory;
import screenplay.tasks.admin.*;
import screenplay.ui.admin.AppointmentAdminSuccess;
import screenplay.ui.admin.AppointmentCancel;
import screenplay.ui.receptionist.AppoinmentCancel;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import java.time.Duration;
import java.util.List;

public class AppointmentAdminSuccessStepDef {

    @When("the admin clicks the {string} button")
    public void adminClicksButton(String btnName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (btnName.equalsIgnoreCase("Lịch Hẹn")) {
            actor.attemptsTo(ClickLichHenMenu.click());
            return;
        }
        if (btnName.equalsIgnoreCase("Tạo lịch hẹn")) {
            actor.attemptsTo(ClickTaoLichHen.click());
            return;
        }
        if (btnName.equalsIgnoreCase("Lưu lịch hẹn")) {
            actor.attemptsTo(ClickSaveAppointment.click());
            return;
        }
        if (btnName.equalsIgnoreCase("Hủy")) {
            actor.attemptsTo(
                WaitUntil.the(AppointmentCancel.BUTTON_HUYLICH, isVisible()),
                Click.on(AppointmentCancel.BUTTON_HUYLICH)
            );
            return;
        }
        if (btnName.equalsIgnoreCase("Xác nhận hủy")) {
            actor.attemptsTo(
                WaitUntil.the(AppoinmentCancel.BUTTON_XACNHANHUY, isVisible()),
                Click.on(AppoinmentCancel.BUTTON_XACNHANHUY)
            );
            return;
        }
        if (btnName.equalsIgnoreCase("Thêm người")) {
            actor.attemptsTo(
                    Scroll.to(AppointmentAdminSuccess.BUTTON_THEMNGUOI),
                    Click.on(AppointmentAdminSuccess.BUTTON_THEMNGUOI)
            );
            return;
        }
        if (!btnName.equalsIgnoreCase("Thêm dòng dịch vụ")) {
            return;
        }

        WebDriver driver = net.serenitybdd.core.Serenity.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By panel = By.cssSelector("aside.admin-slide-in-right");
        By addServiceButton = By.xpath(
                "//aside[contains(@class,'admin-slide-in-right')]//button[contains(normalize-space(.),'Thêm dòng dịch vụ')]"
        );
        By assignmentRows = By.cssSelector("aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5");
        By row1Staff = By.cssSelector("aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(1) select:last-of-type");
        By row2Staff = By.cssSelector("aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(2) select:last-of-type");

        WebElement panelEl = wait.until(ExpectedConditions.visibilityOfElementLocated(panel));
        int before = driver.findElements(assignmentRows).size();

        // Cho UI ổn định sau khi vừa chọn nhân viên dòng 1 trước khi thêm dòng dịch vụ.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        WebElement addBtn = wait.until(ExpectedConditions.presenceOfElementLocated(addServiceButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addBtn);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
        }

        wait.until(d -> d.findElements(assignmentRows).size() > before);
        restoreRow1SpecialistIfReset(actor, wait, row1Staff);

        try {
            WebElement row2StaffEl = wait.until(ExpectedConditions.presenceOfElementLocated(row2Staff));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", row2StaffEl);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[1].offsetTop - 120;", panelEl, row2StaffEl);
        } catch (Exception ignored) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", panelEl);
        }
    }

    private void restoreRow1SpecialistIfReset(Actor actor, WebDriverWait wait, By row1Staff) {
        try {
            String rememberedValue = actor.recall("admin_specialist_value");
            String rememberedText = actor.recall("admin_specialist");
            if ((rememberedValue == null || rememberedValue.isBlank())
                    && (rememberedText == null || rememberedText.isBlank())) {
                return;
            }

            WebElement row1StaffEl = wait.until(ExpectedConditions.presenceOfElementLocated(row1Staff));
            Select select = new Select(row1StaffEl);
            List<WebElement> options = select.getOptions();
            if (options.size() <= 1) {
                return;
            }

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText().trim().toLowerCase();
            boolean isPlaceholder = options.indexOf(selectedOption) == 0
                    || selectedText.isEmpty()
                    || selectedText.contains("chon")
                    || selectedText.contains("select");
            if (!isPlaceholder) {
                return;
            }

            if (rememberedValue != null && !rememberedValue.isBlank()) {
                try {
                    select.selectByValue(rememberedValue);
                    return;
                } catch (Exception ignored) {
                }
            }
            if (rememberedText != null && !rememberedText.isBlank()) {
                for (WebElement option : options) {
                    if (option.getText().trim().equalsIgnoreCase(rememberedText.trim())) {
                        select.selectByVisibleText(option.getText().trim());
                        return;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    @And("the admin enters customer full name {string}")
    public void adminEntersFullName(String name) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(EnterCustomerFullName.withName(name));
    }

    @And("the admin enters customer phone number {string}")
    public void adminEntersPhone(String phone) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(EnterCustomerPhone.withPhone(phone));
    }

    @And("the admin selects service {string}")
    public void adminSelectsService(String service) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsService.withName(service));
    }

    @And("the admin selects suitable specialist {string}")
    public void adminSelectsSpecialist(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsSpecialist.withName(specialist));
    }

    @And("the admin enters appointment date {string}")
    public void adminEntersDate(String date) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminEntersDate.withValue(date));
    }

    @And("the admin selects start time {string}")
    public void adminSelectsTime(String time) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsTime.withValue(time));
    }

    @Then("the system displays a successful appointment creation alert")
    public void systemDisplaysSuccessAlert() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.should(GivenWhenThen.seeThat(AlertSuccessIsVisible.displayed(), Matchers.is(true)));
    }

    @When("the admin clicks {string} on the success alert")
    public void adminClicksOKOnAlert(String ok) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(ClickOKOnAlert.click());
    }

    @Then("the system displays the newly created appointment in the appointment history list")
    public void systemDisplaysAppointmentInHistory() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.should(GivenWhenThen.seeThat(VerifyAdminAppointmentInHistory.withDetails(), Matchers.is(true)));
    }

    private String tempService2;

    @And("the admin selects service {string} for service row 2")
    public void adminSelectsServiceRow2(String service) {
        this.tempService2 = service;
    }

    @And("the admin selects suitable specialist {string} for service row 2")
    public void adminSelectsSpecialistRow2(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(MutilService.withDetails(tempService2, specialist));
    }

    @And("the admin enters companion name {string}")
    public void adminEntersCompanionName(String name) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(EnterCompanionName.withName(name));
    }

    @And("the admin selects companion customer {string} for service row 2")
    public void adminSelectsCompanionCustomerRow2(String companionName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(SelectCompanionCustomer.withName(companionName));
    }

    @And("the admin selects the {string} as the service user")
    public void adminSelectsAsServiceUser(String companionName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(SelectCompanionCustomer.withName(companionName));
    }

    @And("the admin selects service {string} row one")
    public void adminSelectsServiceRowOneTC06(String service) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsServiceRowOne.withName(service));
    }

    @And("the admin selects suitable specialist {string} row one")
    public void adminSelectsSpecialistRowOneTC06(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsSpecialistRowOne.withName(specialist));
    }

    @And("the admin selects suitable specialist {string} for service row two")
    public void adminSelectsSpecialistRowTwoTC06(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsSpecialistRowTwo.withName(specialist));
    }

    @And("the admin selects service {string} for service row two")
    public void adminSelectsServiceRowTwoTC06(String service) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(AdminSelectsServiceRowTwo.withName(service));
    }

    @And("the admin clicks the {string} menu item")
    public void adminClicksMenuItem(String menu) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (menu.equalsIgnoreCase("Lịch hẹn")) {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentCancel.APPOINTMENT_SIDEBAR_MENU, isVisible()),
                    Click.on(AppointmentCancel.APPOINTMENT_SIDEBAR_MENU)
            );
        }
    }

    @And("the admin clicks the eye icon in the action column to view appointment details")
    public void adminClicksEyeIconToViewDetails() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(AppointmentCancel.ICON_MAT_XEMCHITIET, isVisible()),
                Click.on(AppointmentCancel.ICON_MAT_XEMCHITIET)
        );
    }
}
