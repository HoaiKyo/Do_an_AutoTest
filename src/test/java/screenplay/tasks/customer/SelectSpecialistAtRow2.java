package screenplay.tasks.customer;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.customer.AppointmentSuccess;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectSpecialistAtRow2 implements Task {

    private final String specialist;

    public SelectSpecialistAtRow2(String specialist) {
        this.specialist = specialist;
    }

    public static SelectSpecialistAtRow2 withName(String specialist) {
        return instrumented(SelectSpecialistAtRow2.class, specialist);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.COMBOBOX_STAFF2, isVisible())
        );

        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.COMBOBOX_STAFF2.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentSuccess.COMBOBOX_STAFF2.resolveFor(actor));
        List<WebElement> options = select.getOptions();

        List<String> availableSpecialists = new ArrayList<>();
        for (WebElement option : options) {
            String text = option.getText();
            // Lọc các nhân viên hợp lệ (chứa chữ "nhân viên" và không chứa chữ "đã xếp")
            if (text.contains("nhân viên") && !text.contains("đã xếp")) {
                availableSpecialists.add(text);
            }
        }

        if (availableSpecialists.isEmpty()) {
            throw new RuntimeException("No available specialist found for row 2!");
        }

        if ("auto_specialist_2".equals(specialist)) {
            int randomIndex = (int) (Math.random() * availableSpecialists.size());
            String selectedSpecialist = availableSpecialists.get(randomIndex);
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(selectedSpecialist).from(AppointmentSuccess.COMBOBOX_STAFF2)
            );
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(specialist).from(AppointmentSuccess.COMBOBOX_STAFF2)
            );
        }
    }
}
