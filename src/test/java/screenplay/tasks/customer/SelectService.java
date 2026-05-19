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

public class SelectService implements Task {
    private final String service;

    public SelectService(String service) {
        this.service = service;
    }

    public static SelectService withName(String service) {
        return instrumented(SelectService.class, service);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.SERVICE_COMBOBOX, isVisible()));

        // Wait for services to load
        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.SERVICE_COMBOBOX.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentSuccess.SERVICE_COMBOBOX.resolveFor(actor));
        int numOptions = select.getOptions().size();

        String savedService;
        if (service.equals("auto_service") || service.contains("COMBO 3")) {
            // Select random service (index 1 to numOptions - 1)
            int randomIndex = 1 + (int) (Math.random() * (numOptions - 1));
            String selectedText = select.getOptions().get(randomIndex).getText();

            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentSuccess.SERVICE_COMBOBOX));
            savedService = selectedText;
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(service).from(AppointmentSuccess.SERVICE_COMBOBOX));
            savedService = service;
        }
        actor.remember("savedService", savedService);
        actor.remember("service1", savedService);
    }
}
