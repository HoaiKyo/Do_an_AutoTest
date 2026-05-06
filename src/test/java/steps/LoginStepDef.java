package steps;

import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import screenplay.tasks.common.Login;
import screenplay.ui.admin.ManagementAdmin;
import screenplay.ui.common.HomePage;
import screenplay.ui.common.LoginPage;
import screenplay.tasks.common.OpenProfile;
import screenplay.tasks.common.Logout;
import screenplay.ui.receptionist.Management;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
public class LoginStepDef {
    private EnvironmentVariables environmentVariables;

    // Removed unused PageObject instance to prevent Serenity from auto-initializing a browser
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
        getActor(actorName).attemptsTo(
                Ensure.that(HomePage.TEXT_PROFILE).hasText(expectedProfileName));
    }

    @Then("^\"?(.*?)\"? should see the receptionist management page$")
    public void actorShouldSeeTheReceptionistManagementPage(String actorName) {
        getActor(actorName).attemptsTo(
                Ensure.that(Management.VerifyPageReceptionist).isDisplayed());
    }


    @Then("^\"?(.*?)\"? should see the admin management page$")
    public void actorShouldSeeTheAdminManagementPage(String actorName) {
        getActor(actorName).attemptsTo(
                Ensure.that(ManagementAdmin.VerifyAdminManagement).isDisplayed());
    }


    @And("^\"?(.*?)\"? navigates to \"([^\"]*)\"$")
    public void actorNavigatesTo(String actorName, String destination) {
        if (destination.equalsIgnoreCase("Admin Management")) {
            getActor(actorName).attemptsTo(
                    OpenProfile.menu(),
                    Click.on(ManagementAdmin.QuanTriAdmin)
            );
        } else if (destination.equalsIgnoreCase("Receptionist Management")) {
            getActor(actorName).attemptsTo(
                    OpenProfile.menu(),
                    Click.on(Management.QuanLyLeTan)
            );
        }
    }
    @When("^\"?(.*?)\"? activates account \"([^\"]*)\" in Admin page$")
    public void theUserActivatesAccountInAdminPage(String actorName, String email) {
        getActor(actorName).attemptsTo(
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

    @And("^\"?(.*?)\"? logs out from Admin page$")
    public void actorLogsOutFromAdmin(String actorName) {
        getActor(actorName).attemptsTo(
                Click.on(ManagementAdmin.BUTTON_LOGOUTPAGE)
        );
    }

    @When("^\"?(.*?)\"? attempts to login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void actorAttemptsToLogin(String actorName, String username, String password) {
        getActor(actorName).attemptsTo(
                Login.withCredentials(username, password)
        );
    }

    @Then("^\"?(.*?)\"? should see the error message \"([^\"]*)\"$")
    public void actorShouldSeeErrorMessage(String actorName, String expectedMessage) {
        getActor(actorName).attemptsTo(
                WaitUntil.the(LoginPage.ERROR_MESSAGE, isVisible()),
                Ensure.that(LoginPage.ERROR_MESSAGE).hasText(expectedMessage)
        );
    }


}
