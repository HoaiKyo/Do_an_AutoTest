package screenplay.ui.customer;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
public class AppointmentCancel {
    public static final Target BUTTON_CANCEL= Target.the("")
            .located(By.xpath("(//div[contains(@class,'space-y-4')]/div[1]//button)[1]"));
}
