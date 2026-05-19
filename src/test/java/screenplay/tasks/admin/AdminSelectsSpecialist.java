package screenplay.tasks.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import screenplay.ui.admin.AppointmentAdminSuccess;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AdminSelectsSpecialist implements Task {
    private final String specialist;

    public AdminSelectsSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public static AdminSelectsSpecialist withName(String specialist) {
        return instrumented(AdminSelectsSpecialist.class, specialist);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN),
                WaitUntil.the(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN, isVisible())
        );

        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN.resolveFor(actor));
        List<WebElement> options = select.getOptions();

        List<Integer> availableIndices = new ArrayList<>();
        for (int i = 1; i < options.size(); i++) {
            String text = options.get(i).getText();
            if (!text.contains("đã xếp cho người khác")) {
                availableIndices.add(i);
            }
        }

        if (availableIndices.isEmpty()) {
            throw new IllegalStateException("No available specialists found!");
        }

        if ("auto_specialist".equals(specialist)) {
            int randomIndex = availableIndices.get((int) (Math.random() * availableIndices.size()));
            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN)
            );
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(specialist).from(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN)
            );
        }
    }
}
