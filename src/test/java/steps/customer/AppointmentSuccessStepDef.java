package steps.customer;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    @Given("^\"?(.*?)\"? logs in with customer credentials from config$")
    public void logsInWithCustomerCredentialsFromConfig(String actorName) {
        Actor actor = OnStage.theActorCalled(actorName);
        String username = environmentVariables.getProperty("credentials.customer.username");
        String password = environmentVariables.getProperty("credentials.customer.password");
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
        LocalDate dateObj;
        if (date.equals("auto_date")) {
            // Select tomorrow's date
            dateObj = LocalDate.now().plusDays(1);
        } else {
            dateObj = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        String dateToSelect = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        savedDate = dateObj.format(DateTimeFormatter.ofPattern("d/M/yyyy")); // Save for verification

        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.NGAYHEN, isVisible()));

        WebElement element = AppointmentSuccess.NGAYHEN.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) Serenity.getDriver();
        String jsScript = "var val = '" + dateToSelect + "'; " +
                "var el = arguments[0]; " +
                "var setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set; " +
                "if (setter) { " +
                "    setter.call(el, val); " +
                "} else { " +
                "    el.value = val; " +
                "} " +
                "el.dispatchEvent(new Event('input', { bubbles: true })); " +
                "el.dispatchEvent(new Event('change', { bubbles: true }));";
        js.executeScript(jsScript, element);
    }

    @And("the customer selects appointment time {string}")
    public void customerSelectsTime(String time) {
        Actor actor = OnStage.theActorInTheSpotlight();

        if (time.equals("auto_time")) {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.KHUNGGIO, isVisible()));

            // Wait for API to load the time slots (options > 1)
            WebDriver driver = Serenity.getDriver();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor)).getOptions().size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byIndex(1).from(AppointmentSuccess.KHUNGGIO));
            
            Select select = new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor));
            savedTime = select.getFirstSelectedOption().getText().trim();
        } else {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.KHUNGGIO, isVisible()));

            WebDriver driver = Serenity.getDriver();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> new Select(AppointmentSuccess.KHUNGGIO.resolveFor(actor)).getOptions().size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(time).from(AppointmentSuccess.KHUNGGIO));
            savedTime = time;
        }
    }

    @And("the customer selects service {string}")
    public void customerSelectsService(String service) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.SERVICE_COMBOBOX, isVisible()));

        // Wait for services to load
        WebDriver driver = Serenity.getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(AppointmentSuccess.SERVICE_COMBOBOX.resolveFor(actor)).getOptions().size() > 1);

        Select select = new Select(AppointmentSuccess.SERVICE_COMBOBOX.resolveFor(actor));
        int numOptions = select.getOptions().size();

        if (service.equals("auto_service") || service.contains("COMBO 3")) {
            // Select random service (index 1 to numOptions - 1)
            int randomIndex = 1 + (int) (Math.random() * (numOptions - 1));
            String selectedText = select.getOptions().get(randomIndex).getText();

            actor.attemptsTo(
                    SelectFromOptions.byIndex(randomIndex).from(AppointmentSuccess.SERVICE_COMBOBOX));
            savedService = selectedText;
            actor.remember("service1", selectedText);
        } else {
            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(service).from(AppointmentSuccess.SERVICE_COMBOBOX));
            savedService = service;
            actor.remember("service1", service);
        }
    }

    @And("the customer selects specialist {string}")
    public void customerSelectsSpecialist(String specialist) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (specialist.equals("auto_specialist")) {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.STAFF_COMBOBOX, isVisible()));

            // Wait for specialists to load
            WebDriver driver = Serenity.getDriver();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> new Select(AppointmentSuccess.STAFF_COMBOBOX.resolveFor(actor)).getOptions()
                            .size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byIndex(1).from(AppointmentSuccess.STAFF_COMBOBOX));
        } else {
            actor.attemptsTo(
                    WaitUntil.the(AppointmentSuccess.STAFF_COMBOBOX, isVisible()));

            // Wait for specialists to load
            WebDriver driver = Serenity.getDriver();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> new Select(AppointmentSuccess.STAFF_COMBOBOX.resolveFor(actor)).getOptions()
                            .size() > 1);

            actor.attemptsTo(
                    SelectFromOptions.byVisibleText(specialist).from(AppointmentSuccess.STAFF_COMBOBOX));
        }
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
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.POPUP_HOANTAT, isVisible()));
    }

    @When("the customer clicks the {string} button on the popup")
    public void customerClicksClosePopup(String btn) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                Click.on(AppointmentSuccess.BUTTON_CLOSE));
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
        actor.attemptsTo(
                screenplay.tasks.customer.VerifyAppointmentInHistory.withDetails(
                        savedService, 
                        savedTime + " - " + savedDate
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
}
