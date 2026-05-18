package screenplay.tasks.customer;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.customer.AppointmentSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterCompanionName implements Task {

    private final String name;

    public EnterCompanionName(String name) {
        this.name = name;
    }

    public static EnterCompanionName withValue(String name) {
        return instrumented(EnterCompanionName.class, name);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String nameToEnter = "auto_companion_name".equals(name) ? "Companion Test " + System.currentTimeMillis() : name;
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.INPUT_TENNGUOIDICUNG, isVisible()),
                Enter.theValue(nameToEnter).into(AppointmentSuccess.INPUT_TENNGUOIDICUNG)
        );
    }
}
