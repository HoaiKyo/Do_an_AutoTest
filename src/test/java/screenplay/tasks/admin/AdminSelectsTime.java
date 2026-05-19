package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AdminSelectsTime implements Task {
    private final String time;

    public AdminSelectsTime(String time) {
        this.time = time;
    }

    public static AdminSelectsTime withValue(String time) {
        return instrumented(AdminSelectsTime.class, time);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(AppointmentAdminSuccess.INPUT_GIOHEN),
                WaitUntil.the(AppointmentAdminSuccess.INPUT_GIOHEN, isVisible())
        );

        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentAdminSuccess.INPUT_GIOHEN.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentAdminSuccess.INPUT_GIOHEN.resolveFor(actor));
        int numOptions = select.getOptions().size();

        if ("auto_time".equals(time)) {
            int maxSafeIndex = Math.min(5, numOptions - 1);
            int randomIndex = 1 + (int) (Math.random() * maxSafeIndex);
            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentAdminSuccess.INPUT_GIOHEN)
            );
            String selectedText = select.getOptions().get(randomIndex).getText().trim();
            actor.remember("admin_time", selectedText);
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(time).from(AppointmentAdminSuccess.INPUT_GIOHEN)
            );
            actor.remember("admin_time", time);
        }
    }
}
