package screenplay.tasks.common;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import screenplay.ui.admin.ManagementAdmin;
import screenplay.ui.receptionist.Management;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class VerifyPageDisplayed implements Task {
    private final String pageName;

    public VerifyPageDisplayed(String pageName) {
        this.pageName = pageName;
    }

    public static VerifyPageDisplayed forName(String pageName) {
        return instrumented(VerifyPageDisplayed.class, pageName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (pageName.equalsIgnoreCase("Receptionist Management")) {
            actor.attemptsTo(Ensure.that(Management.VerifyPageReceptionist).isDisplayed());
        } else if (pageName.equalsIgnoreCase("Admin Management")) {
            actor.attemptsTo(Ensure.that(ManagementAdmin.VerifyAdminManagement).isDisplayed());
        } else {
            throw new IllegalArgumentException("Unsupported page verification: " + pageName);
        }
    }
}
