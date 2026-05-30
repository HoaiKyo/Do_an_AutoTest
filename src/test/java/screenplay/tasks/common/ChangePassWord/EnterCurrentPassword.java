package screenplay.tasks.common.ChangePassWord;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.ChangePassWord;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterCurrentPassword implements Task {

    private final String password;

    public EnterCurrentPassword(String password) {
        this.password = password;
    }

    public static EnterCurrentPassword withValue(String password) {
        return instrumented(EnterCurrentPassword.class, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ChangePassWord.INPUT_MATKHAU_HIENTAI, isVisible()).forNoMoreThan(5).seconds(),
                Enter.theValue(password).into(ChangePassWord.INPUT_MATKHAU_HIENTAI)
        );
    }
}
