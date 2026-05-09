package steps.common;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.questions.common.ValidationMessFieldLoginEmpty;
import screenplay.ui.common.LoginPage;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import screenplay.tasks.common.ForgotPassWord.ResetPassword;
import screenplay.ui.common.ForgotPassWord;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;

public class ForgotPassWordStepDef {

    @When("^\"?(.*?)\"? clicks on the user icon$")
    public void clicksOnTheUserIcon(String actorName) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Task.where("{0} clicks icon user",
                        WaitUntil.the(LoginPage.ICON_AVATAR, isVisible()),
                        JavaScriptClick.on(LoginPage.ICON_AVATAR)
                )
        );
    }

    @And("^\"?(.*?)\"? clicks on the forgot password link$")
    public void clicksOnTheForgotPassWordLink(String actorName) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(ForgotPassWord.TEXTLINK_FORGOTPASSWORD)
        );
    }

    @When("^\"?(.*?)\"? resets password with phone \"([^\"]*)\", email \"([^\"]*)\", new password \"([^\"]*)\" and confirm password \"([^\"]*)\"$")
    public void resetsPassword(String actorName, String phone, String email, String newPassword, String confirmPassword) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ResetPassword.withInformation(phone, email, newPassword, confirmPassword)
        );
    }

    @When("^\"?(.*?)\"? accepts the alert$")
    public void acceptsTheAlert(String actorName) {
        BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getAlert().accept();
    }

    @And("^\"?(.*?)\"? clicks the login link on forgot password page$")
    public void clicksTheLoginLinkOnForgotPasswordPage(String actorName) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(ForgotPassWord.TEXTLINK_LOGIN)
        );
    }

    @Then("^\"?(.*?)\"? should see required error tooltip for forgot password field \"([^\"]*)\" with message \"([^\"]*)\"$")
    public void shouldSeeRequiredTooltipForForgotPassword(String actorName, String fieldName, String expectedMessage) {
        Target target;
        switch (fieldName) {
            case "Số điện thoại":
                target = ForgotPassWord.TEXTBOX_SDT;
                break;
            case "Email":
                target = ForgotPassWord.TEXTBOX_MAIL;
                break;
            case "Mật khẩu mới":
                target = ForgotPassWord.TEXTBOX_NEWPASSWORD;
                break;
            case "Xác nhận mật khẩu mới":
                target = ForgotPassWord.TEXTBOX_CONFIRMPASSWORD;
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }

        OnStage.theActorInTheSpotlight().should(
                seeThat(ValidationMessFieldLoginEmpty.of(target), equalTo(expectedMessage))
        );
    }
}
