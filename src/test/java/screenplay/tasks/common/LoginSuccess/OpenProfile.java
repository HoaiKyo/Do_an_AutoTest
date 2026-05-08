package screenplay.tasks.common.LoginSuccess;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.HomePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class OpenProfile implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(HomePage.TEXT_PROFILE, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(HomePage.TEXT_PROFILE)
        );
    }

    public static OpenProfile menu() {
        return new OpenProfile();
    }
}
