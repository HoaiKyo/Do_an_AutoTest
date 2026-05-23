package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        WebDriver driver = Serenity.getDriver();

        // Tìm modal panel và dùng JS cuộn nó về đầu trang
        WebElement panel = new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#admin-appointment-panel, aside.admin-slide-in-right")
            ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", panel);
        
        try {
            Thread.sleep(500); // Đợi modal cuộn xong và UI render
        } catch (InterruptedException ignored) {}

        // Chờ service dropdown visible sau khi form cuộn lên đầu
        actor.attemptsTo(
                WaitUntil.the(AppointmentAdminSuccess.COMBOBOX_SERVICE, isVisible()).forNoMoreThan(Duration.ofSeconds(15))
        );

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentAdminSuccess.COMBOBOX_SERVICE.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentAdminSuccess.COMBOBOX_SERVICE.resolveFor(actor));

        // Lấy 1 option của dropdown nhân viên TRƯỚC KHI chọn dịch vụ
        WebElement staffSelect = AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN.resolveFor(actor);
        WebElement oldStaffOption = null;
        try {
            oldStaffOption = staffSelect.findElements(By.tagName("option")).get(1);
        } catch (Exception ignored) {}

        if ("auto_service".equals(service)) {
            String selectedText = select.getOptions().get(1).getText();
            select.selectByIndex(1);
            actor.remember("admin_service", selectedText);
        } else {
            select.selectByVisibleText(service);
            actor.remember("admin_service", service);
        }

        // Đợi option cũ bị Stale (tức là React đã re-render xong danh sách nhân viên mới)
        if (oldStaffOption != null) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.stalenessOf(oldStaffOption));
            } catch (Exception ignored) {}
        }
    }
}
