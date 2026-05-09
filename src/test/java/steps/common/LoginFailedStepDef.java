package steps.common;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import screenplay.questions.common.AlertMessage;
import screenplay.questions.common.ValidationMessFieldLoginEmpty;
import screenplay.ui.common.LoginPage;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginFailedStepDef {

    @Then("^\"?(.*?)\"? should see alert message \"([^\"]*)\"$")
    public void shouldSeeAlertMessage(String actorName, String expectedMessage) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(ExpectedConditions.alertIsPresent())
        );

        OnStage.theActorInTheSpotlight().should(
                seeThat(AlertMessage.text(), equalTo(expectedMessage))
        );
    }

    @Then("^the user should see alert message \"([^\"]*)\" at the top of the screen$")
    public void theUserShouldSeeAlertMessage(String expectedMessage) {
        shouldSeeAlertMessage("the user", expectedMessage);
    }

    @Then("^\"?(.*?)\"? should see required error tooltip for \"([^\"]*)\" with message \"([^\"]*)\"$")
    public void shouldSeeRequiredTooltip(String actorName, String fieldName, String expectedMessage) {
        Target target = fieldName.equalsIgnoreCase("Email") ? LoginPage.INPUT_EMAIL : LoginPage.INPUT_PASSWORD;
        OnStage.theActorInTheSpotlight().should(
                seeThat(ValidationMessFieldLoginEmpty.of(target), equalTo(expectedMessage))
        );
    }
}
