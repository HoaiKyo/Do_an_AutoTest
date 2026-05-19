package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ClickTaoLichHen implements Task {
    public static ClickTaoLichHen click() {
        return instrumented(ClickTaoLichHen.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.BUTTON_TAOLICHHEN, isVisible()),
                Click.on(AppointmentAdminSuccess.BUTTON_TAOLICHHEN)
        );
    }
}
