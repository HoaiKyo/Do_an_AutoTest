package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.Duration;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

public class MutilService implements Task {
    private final String service2;
    private final String specialist2;

    public MutilService(String service2, String specialist2) {
        this.service2 = service2;
        this.specialist2 = specialist2;
    }

    public static MutilService withDetails(String service2, String specialist2) {
        return instrumented(MutilService.class, service2, specialist2);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = Serenity.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.COMBOBOX_SERVICE_2, isPresent())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );

        wait.until(d -> new Select(AppointmentAdminSuccess.COMBOBOX_SERVICE_2.resolveFor(actor)).getOptions().size() > 1);
        Select service2Select = new Select(AppointmentAdminSuccess.COMBOBOX_SERVICE_2.resolveFor(actor));
        if ("auto_service_2".equals(service2) || "auto_service".equals(service2)) {
            String pickedText = service2Select.getOptions().get(1).getText().trim();
            service2Select.selectByIndex(1);
            actor.remember("admin_service_2", pickedText);
        } else {
            service2Select.selectByVisibleText(service2);
            actor.remember("admin_service_2", service2);
        }

        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.COMBOBOX_STAFF_2, isPresent())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );

        By row2StaffBy = By.cssSelector("aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(2) select:last-of-type");
        wait.until(d -> new Select(d.findElement(row2StaffBy)).getOptions().size() > 1);

        if ("auto_specialist_2".equals(specialist2) || "auto_specialist".equals(specialist2)) {
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    Select staff2Select = new Select(driver.findElement(row2StaffBy));
                    List<WebElement> options = staff2Select.getOptions();
                    int pickIndex = -1;
                    String pickedText = null;
                    for (int i = 1; i < options.size(); i++) {
                        String text = options.get(i).getText();
                        if (!text.contains("đã xếp cho người khác") && !text.toLowerCase().contains("admin")) {
                            pickIndex = i;
                            pickedText = text.trim();
                            break;
                        }
                    }
                    if (pickIndex == -1) {
                        throw new IllegalStateException("Khong co chuyen vien kha dung o dong 2!");
                    }
                    staff2Select.selectByIndex(pickIndex);
                    actor.remember("admin_specialist_2", pickedText);
                    ensureRow1SpecialistStillSelected(actor, wait);
                    return;
                } catch (StaleElementReferenceException ignored) {
                }
            }
            throw new IllegalStateException("Khong the chon chuyen vien dong 2 do element bi re-render lien tuc.");
        } else {
            Select staff2Select = new Select(driver.findElement(row2StaffBy));
            staff2Select.selectByVisibleText(specialist2);
            actor.remember("admin_specialist_2", specialist2);
            ensureRow1SpecialistStillSelected(actor, wait);
        }

        try {
            WebElement panelEl = driver.findElement(By.cssSelector("aside.admin-slide-in-right"));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", panelEl);
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    private <T extends Actor> void ensureRow1SpecialistStillSelected(T actor, WebDriverWait wait) {
        String rememberedValue = actor.recall("admin_specialist_value");
        String rememberedText = actor.recall("admin_specialist");
        if ((rememberedValue == null || rememberedValue.isBlank())
                && (rememberedText == null || rememberedText.isBlank())) {
            return;
        }

        By row1StaffBy = By.cssSelector("aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(1) select:last-of-type");
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                Select row1Select = new Select(wait.until(d -> d.findElement(row1StaffBy)));
                List<WebElement> options = row1Select.getOptions();
                WebElement selectedOption = row1Select.getFirstSelectedOption();
                String selectedText = selectedOption.getText().trim().toLowerCase();
                boolean isPlaceholder = options.indexOf(selectedOption) == 0
                        || selectedText.isEmpty()
                        || selectedText.contains("chon")
                        || selectedText.contains("select");
                if (!isPlaceholder) {
                    return;
                }

                if (rememberedValue != null && !rememberedValue.isBlank()) {
                    try {
                        row1Select.selectByValue(rememberedValue);
                        return;
                    } catch (Exception ignored) {
                    }
                }
                if (rememberedText != null && !rememberedText.isBlank()) {
                    row1Select.selectByVisibleText(rememberedText);
                    return;
                }
                return;
            } catch (StaleElementReferenceException ignored) {
            }
        }
    }
}
