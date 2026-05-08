package screenplay.tasks.common;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import screenplay.ui.admin.ManagementAdmin;
import screenplay.ui.receptionist.Management;

import static net.serenitybdd.screenplay.Tasks.instrumented;

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
                    Click.on(ManagementAdmin.QuanTriAdmin)
            );
        } else if (destination.equalsIgnoreCase("Receptionist Management")) {
            actor.attemptsTo(
                    OpenProfile.menu(),
                    Click.on(Management.QuanLyLeTan)
            );
        } else {
            throw new IllegalArgumentException("Unsupported destination: " + destination);
        }
    }
}
