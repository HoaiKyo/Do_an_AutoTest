package screenplay.questions.common;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class AlertMessage {
    public static Question<String> text() {
        return Question.about("the alert message")
                .answeredBy(actor -> BrowseTheWeb.as(actor).getAlert().getText());
    }
}
