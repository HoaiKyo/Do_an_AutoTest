package screenplay.ui.admin;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CategoryManagement {
    public static final Target BUTTON_DANHMUC = Target.the("Button Danh muc ben Admin")
            .located(By.xpath("//span[contains(text(),'Danh mục')]"));

}
