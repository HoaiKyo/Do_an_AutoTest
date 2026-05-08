package steps.common;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import screenplay.questions.common.AlertMessage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginFailedStepDef {

    @Then("^the user should see alert message \"([^\"]*)\" at the top of the screen$")
    public void theUserShouldSeeAlertMessage(String expectedMessage) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(ExpectedConditions.alertIsPresent())
        );

        OnStage.theActorInTheSpotlight().should(
                seeThat(AlertMessage.text(), equalTo(expectedMessage))
        );
    }
}
