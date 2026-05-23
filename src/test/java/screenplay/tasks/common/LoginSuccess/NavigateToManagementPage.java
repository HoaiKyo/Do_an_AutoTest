package screenplay.tasks.common.LoginSuccess;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.ManagementAdmin;
import screenplay.ui.receptionist.Management;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class NavigateToManagementPage implements Task {
    private final String destination;

    public NavigateToManagementPage(String destination) {
        this.destination = destination;
    }

    public static NavigateToManagementPage named(String destination) {
        return instrumented(NavigateToManagementPage.class, destination);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (destination.equalsIgnoreCase("Admin Management")) {
            actor.attemptsTo(
                    OpenProfile.menu(),
                    WaitUntil.the(ManagementAdmin.QuanTriAdmin, isClickable()).forNoMoreThan(Duration.ofSeconds(5)),
                    Click.on(ManagementAdmin.QuanTriAdmin)
            );
        } else if (destination.equalsIgnoreCase("Receptionist Management")) {
            actor.attemptsTo(
                    OpenProfile.menu(),
                    WaitUntil.the(Management.QuanLyLeTan, isClickable()).forNoMoreThan(Duration.ofSeconds(5)),
                    Click.on(Management.QuanLyLeTan)
            );
        } else {
            throw new IllegalArgumentException("Unsupported destination: " + destination);
        }
    }
}
