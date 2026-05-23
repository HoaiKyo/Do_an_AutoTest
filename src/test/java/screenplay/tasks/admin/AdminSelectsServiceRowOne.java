package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AdminSelectsServiceRowOne implements Task {
    private final String service;

    public AdminSelectsServiceRowOne(String service) {
        this.service = service;
    }

    public static AdminSelectsServiceRowOne withName(String service) {
        return instrumented(AdminSelectsServiceRowOne.class, service);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = Serenity.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement selectEl = AppointmentAdminSuccess.COMBOBOX_SERVICE_ROW1_TC06.resolveFor(actor);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectEl);

        wait.until(d -> {
            try {
                WebElement el = AppointmentAdminSuccess.COMBOBOX_SERVICE_ROW1_TC06.resolveFor(actor);
                Select s = new Select(el);
                return s.getOptions().size() > 1;
            } catch (Exception e) {
                return false;
            }
        });

        selectEl = AppointmentAdminSuccess.COMBOBOX_SERVICE_ROW1_TC06.resolveFor(actor);
        Select select = new Select(selectEl);

        if ("auto_service".equals(service) || "auto_service_1".equals(service)) {
            select.selectByIndex(1);
            actor.remember("admin_service", select.getOptions().get(1).getText().trim());
        } else {
            select.selectByVisibleText(service);
            actor.remember("admin_service", service);
        }
    }
}
