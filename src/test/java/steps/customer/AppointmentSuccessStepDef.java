package steps.customer;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import org.openqa.selenium.NoAlertPresentException;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.screenplay.GivenWhenThen;
import org.hamcrest.Matchers;
import screenplay.tasks.common.LoginSuccess.OpenProfile;
import screenplay.ui.common.LoginPage;
import screenplay.ui.customer.AppointmentSuccess;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AppointmentSuccessStepDef {

    private EnvironmentVariables environmentVariables;
    private String savedDate;
    private String savedTime;
    private String savedService;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^\"?(.*?)\"? logs in with (customer|admin|letan) credentials from config$")
    public void logsInWithCredentialsFromConfig(String actorName, String role) {
        Actor actor = OnStage.theActorCalled(actorName);
        String username = environmentVariables.getProperty("credentials." + role + ".username");
        String password = environmentVariables.getProperty("credentials." + role + ".password");
        actor.attemptsTo(screenplay.tasks.common.LoginSuccess.Login.withCredentials(username, password));
    }

    @When("the customer clicks the {string} button on the Banner")
    public void customerClicksBookNow(String buttonName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.BUTTON_DATLICHNGAY, isVisible()),
                Click.on(AppointmentSuccess.BUTTON_DATLICHNGAY));
    }

    @And("the customer selects appointment date {string}")
    public void customerSelectsDate(String date) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.SelectAppointmentDate.withValue(date)
        );
        savedDate = actor.recall("savedDate");
    }

    @And("the customer selects appointment time {string}")
    public void customerSelectsTime(String time) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.SelectAppointmentTime.withValue(time)
        );
        savedTime = actor.recall("savedTime");
    }

    @And("the customer selects service {string}")
    public void customerSelectsService(String service) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.SelectService.withName(service)
        );
        savedService = actor.recall("savedService");
    }

    @And("the customer selects specialist {string}")
    public void customerSelectsSpecialist(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.SelectSpecialist.withName(specialist)
        );
    }

    @And("the customer enters full name {string}")
    public void customerEntersName(String name) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String nameToEnter = name.equals("auto_name") ? "Auto Test " + System.currentTimeMillis() : name;
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.INPUT_NAME, isVisible()),
                Enter.theValue(nameToEnter).into(AppointmentSuccess.INPUT_NAME));
    }

    @And("the customer enters phone number {string}")
    public void customerEntersPhone(String phone) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String phoneToEnter = phone.equals("auto_phone") ? "09" + (long) (Math.random() * 100000000L) : phone;
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.INPUT_SDT, isVisible()),
                Enter.theValue(phoneToEnter).into(AppointmentSuccess.INPUT_SDT));
    }

    @And("the customer clicks the {string} button")
    public void customerClicksBookAppointment(String btnName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (btnName.equals("Book Appointment")) {
            actor.attemptsTo(
                    Click.on(AppointmentSuccess.BUTTON_DATLICHHEN));

            // Wait up to 1s for the alert to appear, but DO NOT accept it here.
            // This allows error test cases to verify the alert text in the next step.
            // Success test cases will dismiss the lingering alert in their next step.
            WebDriver driver = Serenity.getDriver();
            try {
                new WebDriverWait(driver, Duration.ofSeconds(1))
                        .until(ExpectedConditions.alertIsPresent());
            } catch (Exception e) {
                // No alert appeared — continue normally
            }
        } else if (btnName.equals("Next")) {
            actor.attemptsTo(
                    Click.on(AppointmentSuccess.BUTTON_KETIEP));
        } else if (btnName.equals("Add Row")){
            actor.attemptsTo(
                    Click.on(AppointmentSuccess.BUTTON_ADD_SERVICE)
            );
        }
    }

    @Then("the system displays a successful appointment booking popup")
    public void systemDisplaysSuccessPopup() {
        Actor actor = OnStage.theActorInTheSpotlight();

        // Double-check: dismiss any lingering alert before inspecting the DOM
        WebDriver driver = Serenity.getDriver();
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {
            // No alert — proceed
        }

        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.POPUP_HOANTAT, isVisible()).forNoMoreThan(15).seconds());
    }

    @When("the customer clicks the {string} button on the popup")
    public void customerClicksClosePopup(String btn) {
        Actor actor = OnStage.theActorInTheSpotlight();
        WebDriver driver = Serenity.getDriver();

        // Dismiss any lingering alert before trying to interact with the popup
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) { }

        // Get the close button and try to click it immediately
        WebElement closeBtn = AppointmentSuccess.BUTTON_CLOSE.resolveFor(actor);
        try {
            closeBtn.click();
        } catch (Exception e) {
            // Fallback: JS click if element is overlaid by a transition/animation
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
        }
    }

    @And("the customer clicks the Profile icon")
    public void customerClicksProfileIcon() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                OpenProfile.menu());
    }

    @And("the customer clicks {string}")
    public void customerClicksPersonalProfile(String menu) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.BUTTON_HOSOCANHAN, isVisible()),
                Click.on(AppointmentSuccess.BUTTON_HOSOCANHAN));
    }

    @Then("the system displays the newly created appointment in the history")
    public void systemDisplaysNewAppointment() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.should(
                GivenWhenThen.seeThat(
                        screenplay.questions.customer.VerifyAppointmentInHistory.withDetails(
                                savedService,
                                savedTime + " - " + savedDate
                        ),
                        Matchers.is(true)
                )
        );
    }

    @And("the customer selects service at row 2 {string}")
    public void customerSelectsServiceRow2(String service) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.SelectServiceAtRow2.withName(service)
        );
    }

    @And("the customer selects available specialist at row 2 {string}")
    public void customerSelectsSpecialistRow2(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.SelectSpecialistAtRow2.withName(specialist)
        );
    }

    @And("the customer enters companion quantity {string}")
    public void customerEntersCompanionQuantity(String quantity) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.EnterCompanionQuantity.withValue(quantity)
        );
    }

    @And("the customer enters companion name {string}")
    public void customerEntersCompanionName(String name) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                screenplay.tasks.customer.EnterCompanionName.withValue(name)
        );
    }

    @Then("the system displays the alert message {string}")
    public void systemDisplaysAlertMessage(String expectedMessage) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.should(
                GivenWhenThen.seeThat(
                        screenplay.questions.common.TheAlertMessage.text(),
                        Matchers.containsString(expectedMessage)
                )
        );
    }
}
