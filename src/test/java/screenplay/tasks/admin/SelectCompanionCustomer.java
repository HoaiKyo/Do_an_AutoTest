package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

public class SelectCompanionCustomer implements Task {
    private final String companionName;

    public SelectCompanionCustomer(String companionName) {
        this.companionName = companionName;
    }

    public static SelectCompanionCustomer withName(String companionName) {
        return instrumented(SelectCompanionCustomer.class, companionName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String targetName = "auto_companion_name".equals(companionName)
                ? actor.recall("admin_companion_name")
                : companionName;

        WebDriver driver = Serenity.getDriver();

        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.COMBOBOX_TENKHACHDIKEM_ROW2_TC06, isPresent()).forNoMoreThan(Duration.ofSeconds(10)),
                Scroll.to(AppointmentAdminSuccess.COMBOBOX_TENKHACHDIKEM_ROW2_TC06)
        );

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentAdminSuccess.COMBOBOX_TENKHACHDIKEM_ROW2_TC06.resolveFor(actor)).getOptions().size() > 1);

        new Select(AppointmentAdminSuccess.COMBOBOX_TENKHACHDIKEM_ROW2_TC06.resolveFor(actor))
                .selectByVisibleText(targetName);
    }
}
