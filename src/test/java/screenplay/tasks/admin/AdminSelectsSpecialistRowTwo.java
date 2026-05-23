package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AdminSelectsSpecialistRowTwo implements Task {
    private final String specialist;

    public AdminSelectsSpecialistRowTwo(String specialist) {
        this.specialist = specialist;
    }

    public static AdminSelectsSpecialistRowTwo withName(String specialist) {
        return instrumented(AdminSelectsSpecialistRowTwo.class, specialist);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = Serenity.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement selectEl = AppointmentAdminSuccess.COMBOBOX_STAFF_ROW2_TC06.resolveFor(actor);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectEl);

        // Chờ options load xong: có ít nhất 1 option hợp lệ (value != 0, enabled)
        wait.until(d -> {
            try {
                WebElement el = AppointmentAdminSuccess.COMBOBOX_STAFF_ROW2_TC06.resolveFor(actor);
                Select s = new Select(el);
                return s.getOptions().stream().anyMatch(o ->
                    o.isEnabled()
                        && o.getAttribute("value") != null
                        && !o.getAttribute("value").trim().isEmpty()
                        && !"0".equals(o.getAttribute("value").trim())
                );
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return false;
            }
        });

        // Resolve lại sau wait để tránh stale
        selectEl = AppointmentAdminSuccess.COMBOBOX_STAFF_ROW2_TC06.resolveFor(actor);
        Select select = new Select(selectEl);
        List<WebElement> options = select.getOptions();

        String valueToSelect;
        String textToRemember;

        if ("auto_specialist".equalsIgnoreCase(specialist) || "auto_specialist_2".equalsIgnoreCase(specialist)) {
            WebElement picked = options.stream()
                .filter(WebElement::isEnabled)
                .filter(o -> {
                    String v = String.valueOf(o.getAttribute("value")).trim();
                    return !v.isEmpty() && !"0".equals(v);
                })
                .filter(o -> {
                    String t = o.getText().toLowerCase();
                    return !t.contains("admin");
                })
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Không có chuyên viên khả dụng để chọn cho dòng 2"));

            valueToSelect = picked.getAttribute("value");
            textToRemember = picked.getText().trim();
        } else {
            WebElement picked = options.stream()
                .filter(WebElement::isEnabled)
                .filter(o -> o.getText().trim().equals(specialist.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy chuyên viên dòng 2: " + specialist));

            valueToSelect = picked.getAttribute("value");
            textToRemember = picked.getText().trim();
        }

        select.selectByValue(valueToSelect);

        actor.remember("admin_specialist_2", textToRemember);
        actor.remember("admin_specialist_value_2", valueToSelect);
    }
}
