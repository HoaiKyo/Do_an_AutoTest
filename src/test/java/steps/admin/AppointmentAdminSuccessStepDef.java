package steps.admin;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import org.hamcrest.Matchers;
import screenplay.questions.admin.AlertSuccessIsVisible;
import screenplay.questions.admin.VerifyAdminAppointmentInHistory;
import screenplay.tasks.admin.*;

public class AppointmentAdminSuccessStepDef {

    @When("the admin clicks the {string} button")
    public void adminClicksButton(String btnName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (btnName.equalsIgnoreCase("Lịch Hẹn")) {
            actor.attemptsTo(ClickLichHenMenu.click());
        } else if (btnName.equalsIgnoreCase("Tạo lịch hẹn")) {
            actor.attemptsTo(ClickTaoLichHen.click());
        } else if (btnName.equalsIgnoreCase("Lưu lịch hẹn")) {
            actor.attemptsTo(ClickSaveAppointment.click());
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
        actor.should(
                GivenWhenThen.seeThat(AlertSuccessIsVisible.displayed(), Matchers.is(true))
        );
    }

    @When("the admin clicks {string} on the success alert")
    public void adminClicksOKOnAlert(String ok) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(ClickOKOnAlert.click());
    }

    @Then("the system displays the newly created appointment in the appointment history list")
    public void systemDisplaysAppointmentInHistory() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.should(
                GivenWhenThen.seeThat(VerifyAdminAppointmentInHistory.withDetails(), Matchers.is(true))
        );
    }
}
