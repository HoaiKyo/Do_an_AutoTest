package screenplay.tasks.common.ChangePassWord;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.ChangePassWord;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterNewPassword implements Task {

    private final String password;

    public EnterNewPassword(String password) {
        this.password = password;
    }

    public static EnterNewPassword withValue(String password) {
        return instrumented(EnterNewPassword.class, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ChangePassWord.INPUT_MATKHAU_MOI, isVisible()).forNoMoreThan(5).seconds(),
                Enter.theValue(password).into(ChangePassWord.INPUT_MATKHAU_MOI)
        );
    }
}
