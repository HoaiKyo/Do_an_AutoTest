package screenplay.tasks.customer;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import screenplay.ui.customer.AppointmentSuccess;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectAppointmentDate implements Task {
    private final String date;

    public SelectAppointmentDate(String date) {
        this.date = date;
    }

    public static SelectAppointmentDate withValue(String date) {
        return instrumented(SelectAppointmentDate.class, date);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        LocalDate dateObj;
        if (date.equals("auto_date")) {
            // Select tomorrow's date
            dateObj = LocalDate.now().plusDays(1);
        } else {
            dateObj = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        String dateToSelect = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String savedDate = dateObj.format(DateTimeFormatter.ofPattern("d/M/yyyy")); // Save for verification
        actor.remember("savedDate", savedDate);

        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.NGAYHEN, isVisible()));

        WebElement element = AppointmentSuccess.NGAYHEN.resolveFor(actor);
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
