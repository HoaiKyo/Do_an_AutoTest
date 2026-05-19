package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterCustomerPhone implements Task {
    private final String phone;

    public EnterCustomerPhone(String phone) {
        this.phone = phone;
    }

    public static EnterCustomerPhone withPhone(String phone) {
        return instrumented(EnterCustomerPhone.class, phone);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String phoneToEnter = "auto_phone".equals(phone) ? "0352430036" : phone;
        actor.remember("admin_customer_phone", phoneToEnter);
        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.INPUT_SDT, isVisible()),
                Enter.theValue(phoneToEnter).into(AppointmentAdminSuccess.INPUT_SDT)
        );
    }
}
