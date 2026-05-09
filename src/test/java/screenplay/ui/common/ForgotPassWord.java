package screenplay.ui.common;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ForgotPassWord {
    public static final Target TEXTLINK_FORGOTPASSWORD = Target.the("Text Link Quên mật khẩu ở trang đăng nhập")
            .located(By.xpath("//button[contains(text(),'Quên mật khẩu?')]"));
    public static final Target TEXTBOX_SDT = Target.the("Textbox SDT")
            .located(By.xpath("//input[@placeholder='Nhập số điện thoại']"));
    public static final Target TEXTBOX_MAIL = Target.the("Textbox mail")
            .located(By.xpath("//input[@placeholder='Nhập email']"));
    public static final Target TEXTBOX_NEWPASSWORD = Target.the("Text box mật khẩu mới")
            .located(By.xpath("//input[@placeholder='Nhập mật khẩu']"));
    public static final Target TEXTBOX_CONFIRMPASSWORD = Target.the("Textbox xác nhập mật khẩu mới")
            .located(By.xpath("//input[@placeholder='Nhập lại mật khẩu']"));
    public static final Target BUTTON_CHANGEPASS = Target.the("Button Xác nhận đổi mật khẩu")
            .located(By.xpath("//button[contains(text(),'Xác nhận đổi mật khẩu')]"));
    public static final Target TEXTLINK_LOGIN = Target.the(" textlink điều hướng về form Đăng nhập")
            .located(By.xpath("//button[contains(text(),'Đăng nhập')]"));

}
