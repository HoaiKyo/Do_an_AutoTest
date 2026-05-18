package steps.common;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.tasks.common.LoginSuccess.OpenProfile;
import screenplay.tasks.common.LoginSuccess.VerifyProfileName;
import screenplay.ui.common.HomePage;
import screenplay.ui.receptionist.Management;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class LogoutStepDef {

    private Actor getActor(String actorName) {
        String cleanedName = actorName.replaceAll("^\"|\"$", "");
        if (cleanedName.equalsIgnoreCase("the user") || cleanedName.equalsIgnoreCase("user") || cleanedName.equalsIgnoreCase("actor")) {
            return OnStage.theActorInTheSpotlight();
        }
        return OnStage.theActorCalled(cleanedName);
    }

    @And("^\"?(.*?)\"? click Open Profile Menu$")
    public void clickOpenProfileMenu(String actorName) {
        getActor(actorName).attemptsTo(
                OpenProfile.menu()
        );
    }

    @And("^\"?(.*?)\"? click option \"Đăng Xuất\"$")
    public void clickOptionLogout(String actorName) {
        getActor(actorName).attemptsTo(
                WaitUntil.the(HomePage.BUTTON_LOGOUT, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(HomePage.BUTTON_LOGOUT)
        );
    }

    @And("the user click button Logout")
    public void theUserClickButtonLogout() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(Management.BUTTON_DANGXUAT, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(Management.BUTTON_DANGXUAT)
        );
    }


}
