package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterCustomerFullName implements Task {
    private final String name;

    public EnterCustomerFullName(String name) {
        this.name = name;
    }

    public static EnterCustomerFullName withName(String name) {
        return instrumented(EnterCustomerFullName.class, name);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String nameToEnter = "auto_name".equals(name) ? "dungpham" : name;
        actor.remember("admin_customer_name", nameToEnter);
        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.INPUT_NAME, isVisible()),
                Enter.theValue(nameToEnter).into(AppointmentAdminSuccess.INPUT_NAME)
        );
    }
}
