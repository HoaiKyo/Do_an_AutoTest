package steps.receptionist;

import io.cucumber.java.en.And;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;
import screenplay.ui.receptionist.AppoinmentCancel;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ReceptionistCancelStepDef {

    @And("the receptionist clicks the {string} menu item")
    public void receptionistClicksMenuItem(String menu) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (menu.equalsIgnoreCase("Lịch hẹn")) {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentAdminSuccess.BUTTON_LICHHEN_MENU, isVisible()),
                    Click.on(AppointmentAdminSuccess.BUTTON_LICHHEN_MENU)
            );
        }
    }

    @And("the receptionist clicks the eye icon to view appointment details")
    public void receptionistClicksEyeIcon() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(AppoinmentCancel.ICON_MAT_XEMCHITIET, isVisible()),
                Click.on(AppoinmentCancel.ICON_MAT_XEMCHITIET)
        );
    }

    @And("the receptionist clicks the {string} button")
    public void receptionistClicksButton(String button) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (button.equalsIgnoreCase("Hủy")) {
            actor.attemptsTo(
                    WaitUntil.the(AppoinmentCancel.BUTTON_HUYLICH, isVisible()),
                    Click.on(AppoinmentCancel.BUTTON_HUYLICH)
            );
        }
    }

    @And("the admin enters cancellation reason {string}")
    public void adminEntersCancelReason(String reason) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(AppoinmentCancel.INPUT_LYDO, isVisible()),
                Enter.theValue(reason).into(AppoinmentCancel.INPUT_LYDO)
        );
    }
}
