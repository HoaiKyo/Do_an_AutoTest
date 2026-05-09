package screenplay.questions.common;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class ValidationMessFieldLoginEmpty {
    public static Question<String> of(Target target) {
        return actor -> target.resolveFor(actor).getAttribute("validationMessage");
    }
}
