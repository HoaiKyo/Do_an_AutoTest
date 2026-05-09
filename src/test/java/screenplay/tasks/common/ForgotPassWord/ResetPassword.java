package screenplay.tasks.common.ForgotPassWord;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.common.ForgotPassWord;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ResetPassword implements Task {
    private final String phone;
    private final String email;
    private final String newPassword;
    private final String confirmPassword;

    public ResetPassword(String phone, String email, String newPassword, String confirmPassword) {
        this.phone = phone;
        this.email = email;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public static ResetPassword withInformation(String phone, String email, String newPassword, String confirmPassword) {
        return instrumented(ResetPassword.class, phone, email, newPassword, confirmPassword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Task.where("{0} enters phone number",
                        WaitUntil.the(ForgotPassWord.TEXTBOX_SDT, isVisible()),
                        Enter.theValue(phone).into(ForgotPassWord.TEXTBOX_SDT)
                ),
                Task.where("{0} enters email",
                        Enter.theValue(email).into(ForgotPassWord.TEXTBOX_MAIL)
                ),
                Task.where("{0} enters new password",
                        Enter.theValue(newPassword).into(ForgotPassWord.TEXTBOX_NEWPASSWORD)
                ),
                Task.where("{0} enters confirm password",
                        Enter.theValue(confirmPassword).into(ForgotPassWord.TEXTBOX_CONFIRMPASSWORD)
                ),
                Task.where("{0} clicks confirm change password button",
                        Click.on(ForgotPassWord.BUTTON_CHANGEPASS)
                )
        );
    }
}
