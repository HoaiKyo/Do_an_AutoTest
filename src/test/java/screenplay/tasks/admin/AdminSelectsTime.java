package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
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
        WebDriver driver = Serenity.getDriver();

        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.INPUT_GIOHEN, isVisible()),
                Scroll.to(AppointmentAdminSuccess.INPUT_GIOHEN)
        );

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(d -> new Select(AppointmentAdminSuccess.INPUT_GIOHEN.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentAdminSuccess.INPUT_GIOHEN.resolveFor(actor));

        if ("auto_time".equals(time)) {
            String selectedText = select.getOptions().get(1).getText().trim();
            select.selectByIndex(1);
            actor.remember("admin_time", selectedText);
        } else {
            select.selectByVisibleText(time);
            actor.remember("admin_time", time);
        }

        // Click vào dòng "Không có khách đi kèm." để trigger form cuộn lên trên
        try {
            actor.attemptsTo(
                    Click.on(By.xpath("//p[contains(text(),'Không có khách đi kèm.')]"))
            );
            Thread.sleep(500);
        } catch (Exception ignored) {}
    }
}
