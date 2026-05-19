package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
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

public class AdminSelectsService implements Task {
    private final String service;

    public AdminSelectsService(String service) {
        this.service = service;
    }

    public static AdminSelectsService withName(String service) {
        return instrumented(AdminSelectsService.class, service);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (!AppointmentAdminSuccess.COMBOBOX_SERVICE.resolveFor(actor).isVisible()) {
            actor.attemptsTo(
                    Scroll.to(AppointmentAdminSuccess.BUTTON_ADD_SERVICE_ROW),
                    Click.on(AppointmentAdminSuccess.BUTTON_ADD_SERVICE_ROW)
            );
        }

        actor.attemptsTo(
                Scroll.to(AppointmentAdminSuccess.COMBOBOX_SERVICE),
                WaitUntil.the(AppointmentAdminSuccess.COMBOBOX_SERVICE, isVisible())
        );

        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentAdminSuccess.COMBOBOX_SERVICE.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentAdminSuccess.COMBOBOX_SERVICE.resolveFor(actor));
        int numOptions = select.getOptions().size();

        if ("auto_service".equals(service)) {
            int randomIndex = 1 + (int) (Math.random() * (numOptions - 1));
            String selectedText = select.getOptions().get(randomIndex).getText();
            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentAdminSuccess.COMBOBOX_SERVICE)
            );
            actor.remember("admin_service", selectedText);
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(service).from(AppointmentAdminSuccess.COMBOBOX_SERVICE)
            );
            actor.remember("admin_service", service);
        }
    }
}
