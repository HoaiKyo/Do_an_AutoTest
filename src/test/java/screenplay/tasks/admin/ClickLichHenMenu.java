package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ClickLichHenMenu implements Task {
    public static ClickLichHenMenu click() {
        return instrumented(ClickLichHenMenu.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.BUTTON_LICHHEN_MENU, isVisible()),
                Click.on(AppointmentAdminSuccess.BUTTON_LICHHEN_MENU)
        );
    }
}
