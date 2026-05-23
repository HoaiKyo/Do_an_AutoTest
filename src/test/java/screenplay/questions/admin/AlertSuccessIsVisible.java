package screenplay.questions.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AlertSuccessIsVisible implements Question<Boolean> {
    public static AlertSuccessIsVisible displayed() {
        return new AlertSuccessIsVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            WebDriver driver = Serenity.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            if (alertText.toLowerCase().contains("thành công") || alertText.toLowerCase().contains("thành công!")) {
                return true;
            } else {
                throw new AssertionError("Tạo lịch hẹn thất bại! Lỗi từ hệ thống: \"" + alertText + "\"");
            }
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
}
