package screenplay.tasks.common.LoginSuccess;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.LoginPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class Logout implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                OpenProfile.menu(),
                WaitUntil.the(LoginPage.BUTTON_LOGOUT, isClickable()).forNoMoreThan(5).seconds(),
                Click.on(LoginPage.BUTTON_LOGOUT),
                WaitUntil.the(LoginPage.ICON_AVATAR, isClickable()).forNoMoreThan(10).seconds()
        );
    }

    public static Logout now() {
        return instrumented(Logout.class);
    }
}
