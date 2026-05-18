package screenplay.ui.common;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;



public class HomePage {
    public static final Target TEXT_PROFILE = Target.the("Text name profile the user login")
            .located(By.xpath("//span[@class='text-sm font-medium max-w-[100px] truncate']"));
    public static final Target BUTTON_LOGOUT= Target.the("Button Đăng Xuất")
            .located(By.xpath("//button[contains(text(),'Đăng xuất')]"));
}
