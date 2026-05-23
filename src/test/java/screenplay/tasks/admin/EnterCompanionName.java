package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterCompanionName implements Task {
    private final String name;

    public EnterCompanionName(String name) {
        this.name = name;
    }

    public static EnterCompanionName withName(String name) {
        return instrumented(EnterCompanionName.class, name);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String finalName = name;
        if ("auto_companion_name".equals(name)) {
            finalName = "Companion_" + (100 + (int)(Math.random() * 900));
        }
        
        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.INPUT_TENKHACHDIKEM, isVisible()).forNoMoreThan(Duration.ofSeconds(10)),
                Scroll.to(AppointmentAdminSuccess.INPUT_TENKHACHDIKEM),
                Enter.theValue(finalName).into(AppointmentAdminSuccess.INPUT_TENKHACHDIKEM)
        );
        
        actor.remember("admin_companion_name", finalName);
    }
}
