package screenplay.tasks.customer;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.customer.AppointmentSuccess;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectSpecialist implements Task {
    private final String specialist;

    public SelectSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public static SelectSpecialist withName(String specialist) {
        return instrumented(SelectSpecialist.class, specialist);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.STAFF_COMBOBOX, isVisible()));

        // Wait for specialists to load
        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.STAFF_COMBOBOX.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentSuccess.STAFF_COMBOBOX.resolveFor(actor));
        List<WebElement> options = select.getOptions();

        List<Integer> availableIndices = new ArrayList<>();
        for (int i = 1; i < options.size(); i++) {
            String text = options.get(i).getText();
            if (!text.contains("đã xếp cho người khác")) {
                availableIndices.add(i);
            }
        }

        if (availableIndices.isEmpty()) {
            throw new IllegalStateException("No available specialists found!");
        }

        if (specialist.equals("auto_specialist")) {
            // Select first available specialist index
            int firstAvailableIndex = availableIndices.get(0);
            actor.attemptsTo(
                    SelectFromOptions.byIndex(firstAvailableIndex).from(AppointmentSuccess.STAFF_COMBOBOX));
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(specialist).from(AppointmentSuccess.STAFF_COMBOBOX));
        }
    }
}
