package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ClickOKOnAlert implements Task {
    public static ClickOKOnAlert click() {
        return instrumented(ClickOKOnAlert.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).getAlert().accept();
    }
}
