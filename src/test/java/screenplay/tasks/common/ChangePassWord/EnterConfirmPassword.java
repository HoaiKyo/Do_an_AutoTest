package screenplay.tasks.common.ChangePassWord;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.ChangePassWord;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnterConfirmPassword implements Task {

    private final String password;

    public EnterConfirmPassword(String password) {
        this.password = password;
    }

    public static EnterConfirmPassword withValue(String password) {
        return instrumented(EnterConfirmPassword.class, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ChangePassWord.INPUT_XACNHAN_MATKHAU, isVisible()).forNoMoreThan(5).seconds(),
                Enter.theValue(password).into(ChangePassWord.INPUT_XACNHAN_MATKHAU)
        );
    }
}
