package screenplay.tasks.common.LoginSuccess;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.HomePage;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class VerifyProfileName implements Task {
    private final String expectedProfileName;

    public VerifyProfileName(String expectedProfileName) {
        this.expectedProfileName = expectedProfileName;
    }

    public static VerifyProfileName isVisibleAs(String expectedProfileName) {
        return instrumented(VerifyProfileName.class, expectedProfileName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (expectedProfileName == null || expectedProfileName.isEmpty()) {
            actor.attemptsTo(
                    WaitUntil.the(HomePage.TEXT_PROFILE, isNotVisible()).forNoMoreThan(10).seconds()
            );
        } else {
            actor.attemptsTo(
                    WaitUntil.the(HomePage.TEXT_PROFILE, isVisible()).forNoMoreThan(10).seconds(),
                    Ensure.that(HomePage.TEXT_PROFILE).hasText(expectedProfileName)
            );
        }
    }
}
