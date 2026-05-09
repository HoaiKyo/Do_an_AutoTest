package screenplay.ui.common;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import net.serenitybdd.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;



public class LoginPage extends PageObject {
    public static final Target INPUT_EMAIL = Target.the("Email input field")
            .located(By.xpath("//input[@placeholder='Nhập email']"));
    public static final Target INPUT_PASSWORD = Target.the("Password input field")
            .located(By.xpath("//input[@placeholder='Nhập mật khẩu']"));
    public static final Target BUTTON_LOGIN = Target.the("Button login")
            .located(By.xpath("//button[@type='submit']"));
    public static final Target ICON_AVATAR = Target.the("Icon avatar to open login")
            .located(By.id("login-button"));
    public static final Target BUTTON_LOGOUT = Target.the("Button Logout")
            .located(By.xpath("//button[contains(text(),'Đăng xuất')]"));
    public static final Target ERROR_MESSAGE = Target.the("Error message for failed login")
            .located(By.xpath("//div[contains(@class,'toast-error')] | //p[contains(@class,'text-red')]"));
    public static final Target TEXTLINK_FORGOTPASSWORD = Target.the("Text Link Quên mật khẩu ở trang đăng nhập")
            .located(By.xpath("//button[contains(text(),'Quên mật khẩu?')]"));
}
