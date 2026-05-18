package screenplay.tasks.customer;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.customer.AppointmentSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterCompanionQuantity implements Task {

    private final String quantity;

    public EnterCompanionQuantity(String quantity) {
        this.quantity = quantity;
    }

    public static EnterCompanionQuantity withValue(String quantity) {
        return instrumented(EnterCompanionQuantity.class, quantity);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.INPUT_SOLUONG_NGUOIDICUNG, isVisible()),
                Enter.theValue(quantity).into(AppointmentSuccess.INPUT_SOLUONG_NGUOIDICUNG)
        );
    }
}
