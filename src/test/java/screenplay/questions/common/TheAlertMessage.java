package screenplay.questions.common;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TheAlertMessage implements Question<String> {

    public static TheAlertMessage text() {
        return new TheAlertMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        WebDriver driver = Serenity.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String text = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            return text;
        } catch (Exception e) {
            return ""; // Return empty if no alert
        }
    }
}
