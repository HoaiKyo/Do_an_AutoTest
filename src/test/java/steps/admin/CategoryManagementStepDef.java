package steps.admin;

import io.cucumber.java.en.And;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import screenplay.ui.admin.CategoryManagement;

public class CategoryManagementStepDef {

    private Actor getActor(String actorName) {
        String cleanedName = actorName.replaceAll("^\"|\"$", "");
        if (cleanedName.equalsIgnoreCase("the user") || cleanedName.equalsIgnoreCase("user") || cleanedName.equalsIgnoreCase("actor") || cleanedName.equalsIgnoreCase("<actor>")) {
            return OnStage.theActorInTheSpotlight();
        }
        return OnStage.theActorCalled(cleanedName);
    }

    @And("^\"?(.*?)\"? click \"([^\"]*)\"$")
    public void theUserClick(String actorName, String buttonName) {
        if (buttonName.equalsIgnoreCase("Danh Mục")) {
            getActor(actorName).attemptsTo(
                    Click.on(CategoryManagement.BUTTON_DANHMUC)
            );
        }
    }
}
