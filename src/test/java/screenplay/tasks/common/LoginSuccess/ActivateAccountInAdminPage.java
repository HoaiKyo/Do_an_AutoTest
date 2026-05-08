package screenplay.tasks.common.LoginSuccess;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.ManagementAdmin;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ActivateAccountInAdminPage implements Task {
    private final String email;

    public ActivateAccountInAdminPage(String email) {
        this.email = email;
    }

    public static ActivateAccountInAdminPage forEmail(String email) {
        return instrumented(ActivateAccountInAdminPage.class, email);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                OpenProfile.menu(),
                WaitUntil.the(ManagementAdmin.QuanTriAdmin, isVisible()),
                Click.on(ManagementAdmin.QuanTriAdmin),
                WaitUntil.the(ManagementAdmin.BUTTON_NGUOIDUNG, isVisible()),
                Scroll.to(ManagementAdmin.BUTTON_NGUOIDUNG),
                Click.on(ManagementAdmin.BUTTON_NGUOIDUNG),
                WaitUntil.the(ManagementAdmin.BUTTON_ACTIVEUSER, isVisible()),
                Click.on(ManagementAdmin.BUTTON_ACTIVEUSER)
        );
    }
}
