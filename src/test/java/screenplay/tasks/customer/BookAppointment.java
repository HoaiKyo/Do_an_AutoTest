package screenplay.tasks.customer;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.customer.AppointmentSuccess;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class BookAppointment implements Task {

    private final String date;
    private final String time;
    private final String service;
    private final String specialist;
    private final String name;
    private final String phone;

    public BookAppointment(String date, String time, String service, String specialist, String name, String phone) {
        this.date = date;
        this.time = time;
        this.service = service;
        this.specialist = specialist;
        this.name = name;
        this.phone = phone;
    }

    public static BookAppointment withDetails(String date, String time, String service, String specialist, String name, String phone) {
        return instrumented(BookAppointment.class, date, time, service, specialist, name, phone);
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

        String nameToEnter = "auto_name".equals(name) ? "Auto Test " + System.currentTimeMillis() : name;
        String phoneToEnter = "auto_phone".equals(phone) ? "09" + (long)(Math.random() * 100000000L) : phone;

        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.BUTTON_DATLICHNGAY, isVisible()),
                Click.on(AppointmentSuccess.BUTTON_DATLICHNGAY),
                
                WaitUntil.the(AppointmentSuccess.NGAYHEN, isVisible())
        );

        WebElement element = AppointmentSuccess.NGAYHEN.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) Serenity.getDriver();
        js.executeScript(
            "arguments[0].value = '" + dateToSelect + "'; " +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", 
            element
        );

        if ("auto_time".equals(time)) {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.KHUNGGIO, isVisible())
            );
            
            // Wait for API to load the time slots (options > 1)
            WebDriver driver = Serenity.getDriver();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor)).getOptions().size() > 1);

            Select select = new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor));
            int numOptions = select.getOptions().size();
            int maxSafeIndex = Math.min(5, numOptions - 1);
            int randomIndex = 1 + (int) (Math.random() * maxSafeIndex);

            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentSuccess.KHUNGGIO)
            );
        } else {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.KHUNGGIO, isVisible())
            );

            WebDriver driver = Serenity.getDriver();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor)).getOptions().size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(time).from(AppointmentSuccess.KHUNGGIO)
            );
        }

        actor.attemptsTo(
                Click.on(AppointmentSuccess.BUTTON_KETIEP),
                WaitUntil.the(AppointmentSuccess.SERVICE_COMBOBOX, isVisible())
        );

        // Wait for service combobox to load options
        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.SERVICE_COMBOBOX.resolveFor(actor)).getOptions().size() > 1);

        Select selectService = new Select(AppointmentSuccess.SERVICE_COMBOBOX.resolveFor(actor));
        int numOptionsService = selectService.getOptions().size();

        if ("auto_service".equals(service) || service.contains("COMBO 3")) {
            int randomIndex = 1 + (int)(Math.random() * (numOptionsService - 1));
            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentSuccess.SERVICE_COMBOBOX)
            );
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(service).from(AppointmentSuccess.SERVICE_COMBOBOX)
            );
        }

        if ("auto_specialist".equals(specialist)) {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.STAFF_COMBOBOX, isVisible())
            );
            
            // Wait for staff combobox to load options
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> new Select(AppointmentSuccess.STAFF_COMBOBOX.resolveFor(actor)).getOptions().size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byIndex(1).from(AppointmentSuccess.STAFF_COMBOBOX)
            );
        } else {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.STAFF_COMBOBOX, isVisible())
            );
            
            // Wait for staff combobox to load options
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> new Select(AppointmentSuccess.STAFF_COMBOBOX.resolveFor(actor)).getOptions().size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(specialist).from(AppointmentSuccess.STAFF_COMBOBOX)
            );
        }

        actor.attemptsTo(
                Click.on(AppointmentSuccess.BUTTON_KETIEP),
                
                WaitUntil.the(AppointmentSuccess.INPUT_NAME, isVisible()),
                Enter.theValue(nameToEnter).into(AppointmentSuccess.INPUT_NAME),
                
                WaitUntil.the(AppointmentSuccess.INPUT_SDT, isVisible()),
                Enter.theValue(phoneToEnter).into(AppointmentSuccess.INPUT_SDT),
                
                Click.on(AppointmentSuccess.BUTTON_DATLICHHEN)
        );
    }
}
