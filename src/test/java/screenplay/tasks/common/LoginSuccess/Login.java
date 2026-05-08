package screenplay.tasks.common.LoginSuccess;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.LoginPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Login implements Task {
    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Login withCredentials(String username, String password) {
        return instrumented(Login.class, username, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Task.where("{0} clicks icon user",
                        WaitUntil.the(LoginPage.ICON_AVATAR, isVisible()),
                        JavaScriptClick.on(LoginPage.ICON_AVATAR)
                ),
                Task.where("{0} enters the username",
                        Enter.theValue(username).into(LoginPage.INPUT_EMAIL)
                ),
                Task.where("{0} enters the password",
                        Enter.theValue(password).into(LoginPage.INPUT_PASSWORD)
                ),
                Task.where("{0} clicks login button",
                        Click.on(LoginPage.BUTTON_LOGIN)
                )
        );
    }
}
