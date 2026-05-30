package screenplay.tasks.customer;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.customer.AppointmentSuccess;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectAppointmentTime implements Task {
    private final String time;

    public SelectAppointmentTime(String time) {
        this.time = time;
    }

    public static SelectAppointmentTime withValue(String time) {
        return instrumented(SelectAppointmentTime.class, time);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.KHUNGGIO, isVisible()));

        // Wait for API to load the time slots (options > 1)
        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor)).getOptions().size() > 1);

        String savedTime;
        if (time.equals("auto_time")) {
            Select select = new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor));
            int numOptions = select.getOptions().size();
            int maxSafeIndex = Math.min(5, numOptions - 1);
            int randomIndex = 1 + (int) (Math.random() * maxSafeIndex);
            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentSuccess.KHUNGGIO));
            
            savedTime = select.getOptions().get(randomIndex).getText().trim();
        } else {
            Select select = new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor));
            java.util.List<org.openqa.selenium.WebElement> options = select.getOptions();
            String matchedText = time;
            boolean found = false;
            for (org.openqa.selenium.WebElement option : options) {
                if (option.getText().contains(time)) {
                    matchedText = option.getText().trim();
                    found = true;
                    break;
                }
            }
            if (found) {
                actor.attemptsTo(SelectFromOptions.byVisibleText(matchedText).from(AppointmentSuccess.KHUNGGIO));
                savedTime = matchedText;
            } else {
                actor.attemptsTo(SelectFromOptions.byVisibleText(time).from(AppointmentSuccess.KHUNGGIO));
                savedTime = time;
            }
        }
        actor.remember("savedTime", savedTime);
    }
}
