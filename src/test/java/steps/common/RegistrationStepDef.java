package steps.common;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.tasks.common.Registration.EnterRegistrationInfo;
import screenplay.questions.common.ProfileName;
import screenplay.ui.common.Registration;
import screenplay.ui.common.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import net.serenitybdd.screenplay.targets.Target;
import screenplay.questions.common.ValidationMessFieldLoginEmpty;
import screenplay.questions.common.TheAlertMessage;
import org.hamcrest.Matchers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationStepDef {

    @And("^\"?(.*?)\"? clicks on the Register link$")
    public void clicksOnTheRegisterLink(String actorName) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(Registration.TEXTLINK_DANGKINGAY)
        );
    }

    @When("^the customer enters registration info with name \"([^\"]*)\", phone \"([^\"]*)\", email \"([^\"]*)\", password \"([^\"]*)\", confirm password \"([^\"]*)\"$")
    public void entersRegistrationInfo(String name, String phone, String email, String password, String confirmPassword) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                EnterRegistrationInfo.withData(name, phone, email, password, confirmPassword)
        );
    }



    @And("^the customer clicks \"OK\" on the success alert$")
    public void clicksOkOnAlert() {
        WebDriver driver = BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @And("^the customer enters the registered email \"([^\"]*)\"$")
    public void entersRegisteredEmail(String email) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(LoginPage.BUTTON_LOGIN, isVisible()).forNoMoreThan(10).seconds(),
                WaitUntil.the(LoginPage.INPUT_EMAIL, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(email).into(LoginPage.INPUT_EMAIL)
        );
    }

    @And("^the customer enters the registered password \"([^\"]*)\"$")
    public void entersRegisteredPassword(String password) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Enter.theValue(password).into(LoginPage.INPUT_PASSWORD)
        );
    }



    @Then("^the system should display the customer's profile name \"([^\"]*)\"$")
    public void verifyProfileName(String expectedName) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ProfileName.displayed(), equalTo(expectedName))
        );
    }

    @Then("^the system should display \"([^\"]*)\" for registration field \"([^\"]*)\" with message \"([^\"]*)\"$")
    public void shouldSeeRequiredErrorForRegistration(String errorType, String fieldName, String expectedMessage) {
        if ("alert".equalsIgnoreCase(errorType)) {
            OnStage.theActorInTheSpotlight().should(
                    seeThat(TheAlertMessage.text(), Matchers.containsString(expectedMessage))
            );
            return;
        }

        Target target;
        switch (fieldName) {
            case "Họ và tên":
                target = Registration.INPUT_FULLNAME;
                break;
            case "Số điện thoại":
                target = Registration.INPUT_SDT;
                break;
            case "Email":
                target = Registration.INPUT_MAIL;
                break;
            case "Mật khẩu":
                target = Registration.INPUT_PASSWORD;
                break;
            case "Xác nhận mật khẩu":
                target = Registration.INPUT_CONFIRM_PASS;
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }

        OnStage.theActorInTheSpotlight().should(
                seeThat(ValidationMessFieldLoginEmpty.of(target), equalTo(expectedMessage))
        );
    }
}
