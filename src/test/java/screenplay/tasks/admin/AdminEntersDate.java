package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AdminEntersDate implements Task {
    private final String date;

    public AdminEntersDate(String date) {
        this.date = date;
    }

    public static AdminEntersDate withValue(String date) {
        return instrumented(AdminEntersDate.class, date);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        LocalDate dateObj;
        if ("auto_date".equals(date)) {
            // Select tomorrow's date
            dateObj = LocalDate.now().plusDays(1);
        } else {
            dateObj = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        String dateToSelect = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String savedDate = dateObj.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        actor.remember("admin_date", savedDate);

        actor.attemptsTo(
                Scroll.to(AppointmentAdminSuccess.INPUT_NGAYHEN),
                WaitUntil.the(AppointmentAdminSuccess.INPUT_NGAYHEN, isVisible())
        );

        WebElement element = AppointmentAdminSuccess.INPUT_NGAYHEN.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) Serenity.getDriver();
        String jsScript = "var val = '" + dateToSelect + "'; " +
                "var el = arguments[0]; " +
                "var setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set; " +
                "if (setter) { " +
                "    setter.call(el, val); " +
                "} else { " +
                "    el.value = val; " +
                "} " +
                "el.dispatchEvent(new Event('input', { bubbles: true })); " +
                "el.dispatchEvent(new Event('change', { bubbles: true }));";
        js.executeScript(jsScript, element);
    }
}
