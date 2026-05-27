package screenplay.questions.common;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.HomePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ProfileName implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        actor.attemptsTo(WaitUntil.the(HomePage.TEXT_PROFILE, isVisible()).forNoMoreThan(10).seconds());
        return Text.of(HomePage.TEXT_PROFILE).answeredBy(actor);
    }

    public static Question<String> displayed() {
        return new ProfileName();
    }
}
