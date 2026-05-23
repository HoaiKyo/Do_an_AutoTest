package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ClickSaveAppointment implements Task {
    public static ClickSaveAppointment click() {
        return Tasks.instrumented(ClickSaveAppointment.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = Serenity.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By panelBy = By.cssSelector("div.fixed.inset-0.z-50 > aside.admin-slide-in-right");
        By saveBy  = By.xpath(".//button[normalize-space()='Lưu lịch hẹn']");

        for (int i = 0; i < 5; i++) {
            try {
                WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(panelBy));

                // đóng dropdown native nếu còn mở (rất hay chặn scroll/click)
                new Actions(driver).sendKeys(Keys.ESCAPE).perform();

                // Cuộn tới mục "Ghi chú" trước để đảm bảo phần đáy form hiện lên
                try {
                    WebElement noteLabel = panel.findElement(By.xpath(".//label[normalize-space()='Ghi chú']"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", noteLabel);
                    Thread.sleep(500);
                } catch (Exception ignored) {
                    // Fallback: nếu không thấy nhãn Ghi chú thì cuộn toàn bộ panel
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollTop = arguments[0].scrollHeight;", panel
                    );
                }

                // tìm nút Lưu bên trong panel sau khi cuộn
                WebElement saveBtn = panel.findElement(saveBy);

                wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                // retry
            }
        }
    }
}
