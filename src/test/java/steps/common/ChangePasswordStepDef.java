package steps.common;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import screenplay.questions.common.ValidationMessFieldLoginEmpty;
import screenplay.ui.common.ChangePassWord;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import screenplay.tasks.common.ChangePassWord.EnterCurrentPassword;
import screenplay.tasks.common.ChangePassWord.EnterNewPassword;
import screenplay.tasks.common.ChangePassWord.EnterConfirmPassword;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import org.openqa.selenium.JavascriptExecutor;
import net.serenitybdd.core.Serenity;

public class ChangePasswordStepDef {

    @And("the customer enters current password {string}")
    public void customerEntersCurrentPassword(String currentPassword) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                EnterCurrentPassword.withValue(currentPassword)
        );
    }

    @And("the customer enters new password {string}")
    public void customerEntersNewPassword(String newPassword) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                EnterNewPassword.withValue(newPassword)
        );
    }

    @And("the customer enters confirm password {string}")
    public void customerEntersConfirmPassword(String confirmPassword) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                EnterConfirmPassword.withValue(confirmPassword)
        );
    }

    @And("^the customer clicks the\\s+button \"([^\"]*)\"$")
    public void customerClicksTheButton(String buttonName) {
        Actor actor = OnStage.theActorInTheSpotlight();
        if (buttonName.equals("Lưu thay đổi")) {
            actor.attemptsTo(
                    Click.on(ChangePassWord.BUTTON_LUUTHAYDOI)
            );
        } else if (buttonName.equals("Đổi mật khẩu")) {
            actor.attemptsTo(
                    Click.on(ChangePassWord.BUTTON_DOIMATKHAU)
            );
        }
    }

    @And("the customer clicks button with text {string}")
    public void customerClicksButtonWithText(String buttonText) {
        Actor actor = OnStage.theActorInTheSpotlight();
        
        // Create a dynamic target using the exact text passed from the feature file
        Target dynamicButton = Target.the(buttonText + " button")
                .located(By.xpath("//button[contains(.,'" + buttonText + "')]"));
                
        actor.attemptsTo(
                WaitUntil.the(dynamicButton, isVisible())
        );
        
        WebDriver driver = Serenity.getDriver();
        WebElement btn = dynamicButton.resolveFor(actor);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        
        // Sleep to wait for React state updates (e.g., API loading finish)
        try { Thread.sleep(500); } catch (Exception e) {}
        
        // MUST re-resolve the element here because React re-renders the component when API calls finish, making the old btn stale!
        WebElement freshBtn = dynamicButton.resolveFor(actor);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", freshBtn);
    }
    @Then("the customer should see required error tooltip for {string} with message {string}")
    public void customerShouldSeeRequiredErrorTooltipFor(String fieldName, String expectedMessage) {
        Target target;
        switch (fieldName) {
            case "Mật khẩu hiện tại":
                target = ChangePassWord.INPUT_MATKHAU_HIENTAI;
                break;
            case "Mật khẩu mới":
                target = ChangePassWord.INPUT_MATKHAU_MOI;
                break;
            case "Xác nhận mật khẩu":
                target = ChangePassWord.INPUT_XACNHAN_MATKHAU;
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
        
        Actor actor = OnStage.theActorInTheSpotlight();
        WebElement element = target.resolveFor(actor);
        actor.attemptsTo(
                WaitUntil.the(ExpectedConditions.attributeToBe(element, "validationMessage", expectedMessage))
        );

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        actor.should(
                GivenWhenThen.seeThat(
                        ValidationMessFieldLoginEmpty.of(target),
                        Matchers.equalTo(expectedMessage)
                )
        );
    }

}
