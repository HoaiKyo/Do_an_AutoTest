package screenplay.tasks.common;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import screenplay.ui.admin.ManagementAdmin;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class LogoutFromAdminPage implements Task {

    public static LogoutFromAdminPage now() {
        return instrumented(LogoutFromAdminPage.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ManagementAdmin.BUTTON_LOGOUTPAGE)
        );
    }
}
