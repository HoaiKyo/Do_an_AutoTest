package screenplay.tasks.common.Registration;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.Registration;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class EnterRegistrationInfo implements Task {

    private final String fullName;
    private final String phone;
    private final String email;
    private final String password;
    private final String confirmPassword;

    public EnterRegistrationInfo(String fullName, String phone, String email, String password, String confirmPassword) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public static EnterRegistrationInfo withData(String fullName, String phone, String email, String password, String confirmPassword) {
        return instrumented(EnterRegistrationInfo.class, fullName, phone, email, password, confirmPassword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(Registration.INPUT_FULLNAME, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(fullName).into(Registration.INPUT_FULLNAME),
                Enter.theValue(phone).into(Registration.INPUT_SDT),
                Enter.theValue(email).into(Registration.INPUT_MAIL),
                Enter.theValue(password).into(Registration.INPUT_PASSWORD),
                Enter.theValue(confirmPassword).into(Registration.INPUT_CONFIRM_PASS)
        );
    }
}
