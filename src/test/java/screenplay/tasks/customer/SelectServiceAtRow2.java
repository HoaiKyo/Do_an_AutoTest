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

public class SelectServiceAtRow2 implements Task {

    private final String service;

    public SelectServiceAtRow2(String service) {
        this.service = service;
    }

    public static SelectServiceAtRow2 withName(String service) {
        return instrumented(SelectServiceAtRow2.class, service);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.COMBOBOX_SERVICE2, isVisible())
        );

        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.COMBOBOX_SERVICE2.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentSuccess.COMBOBOX_SERVICE2.resolveFor(actor));
        int numOptions = select.getOptions().size();

        if ("auto_service_2".equals(service)) {
            int randomIndex = 1 + (int) (Math.random() * (numOptions - 1));
            String selectedText = select.getOptions().get(randomIndex).getText();
            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentSuccess.COMBOBOX_SERVICE2)
            );
            actor.remember("service2", selectedText);
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(service).from(AppointmentSuccess.COMBOBOX_SERVICE2)
            );
            actor.remember("service2", service);
        }
    }
}
