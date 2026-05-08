package steps.common;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.model.util.EnvironmentVariables;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import screenplay.tasks.common.LoginSuccess.ActivateAccountInAdminPage;
import screenplay.tasks.common.LoginSuccess.Login;
import screenplay.tasks.common.LoginSuccess.LogoutFromAdminPage;
import screenplay.tasks.common.LoginSuccess.NavigateToManagementPage;
import screenplay.tasks.common.LoginSuccess.VerifyPageDisplayed;
import screenplay.tasks.common.LoginSuccess.VerifyProfileName;
import screenplay.ui.common.LoginPage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginSuccessStepDef {
    private EnvironmentVariables environmentVariables;

    // Removed unused PageObject instance to prevent Serenity from auto-initializing
    // a browser
    // private LoginPage loginPage;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    private Actor getActor(String actorName) {
        // Loại bỏ dấu ngoặc kép nếu có
        String cleanedName = actorName.replaceAll("^\"|\"$", "");
        if (cleanedName.equalsIgnoreCase("the user") || cleanedName.equalsIgnoreCase("actor")) {
            return OnStage.theActorInTheSpotlight();
        }
        return OnStage.theActorCalled(cleanedName);
    }

    @Given("^\"?(.*?)\"? go to bml login page$")
    public void goToBmlLoginPage(String actorName) {
        String baseUrl = environmentVariables.optionalProperty("webdriver.base.url")
                .orElse("http://localhost:3000");
        getActor(actorName).attemptsTo(Open.url(baseUrl));
    }

    @When("^\"?(.*?)\"? logs in with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void logsInWithUsernameAndPassword(String actorName, String username, String password) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (actor == null) {
            actor = getActor(actorName);
        }
        actor.attemptsTo(Login.withCredentials(username, password));
    }

    @Then("^\"?(.*?)\"? should see the profile name as \"([^\"]*)\"$")
    public void shouldSeeTheProfileNameAs(String actorName, String expectedProfileName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (actor == null) {
            actor = getActor(actorName);
        }
        actor.attemptsTo(
                VerifyProfileName.isVisibleAs(expectedProfileName));
    }

    @Then("^\"?(.*?)\"? should see the receptionist management page$")
    public void actorShouldSeeTheReceptionistManagementPage(String actorName) {
        getActor(actorName).attemptsTo(
                VerifyPageDisplayed.forName("Receptionist Management"));
    }

    @Then("^\"?(.*?)\"? should see the admin management page$")
    public void actorShouldSeeTheAdminManagementPage(String actorName) {
        getActor(actorName).attemptsTo(
                VerifyPageDisplayed.forName("Admin Management"));
    }

    @And("^\"?(.*?)\"? navigates to \"([^\"]*)\"$")
    public void actorNavigatesTo(String actorName, String destination) {
        getActor(actorName).attemptsTo(
                NavigateToManagementPage.named(destination));
    }

    @When("^\"?(.*?)\"? activates account \"([^\"]*)\" in Admin page$")
    public void theUserActivatesAccountInAdminPage(String actorName, String email) {
        getActor(actorName).attemptsTo(
                ActivateAccountInAdminPage.forEmail(email));
    }

    @And("^\"?(.*?)\"? logs out from Admin page$")
    public void actorLogsOutFromAdmin(String actorName) {
        getActor(actorName).attemptsTo(
                LogoutFromAdminPage.now());
    }

    @When("^\"?(.*?)\"? attempts to login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void actorAttemptsToLogin(String actorName, String username, String password) {
        getActor(actorName).attemptsTo(
                Login.withCredentials(username, password));
    }

    @Then("^\"?(.*?)\"? should see the error message \"([^\"]*)\"$")
    public void actorShouldSeeErrorMessage(String actorName, String expectedMessage) {
        getActor(actorName).attemptsTo(
                WaitUntil.the(LoginPage.ERROR_MESSAGE, isVisible()),
                Ensure.that(LoginPage.ERROR_MESSAGE).hasText(expectedMessage));
    }

}
