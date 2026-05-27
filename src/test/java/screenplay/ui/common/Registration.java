package screenplay.ui.common;

import org.openqa.selenium.By;
import net.serenitybdd.screenplay.targets.Target;

public class Registration {
    public static final Target TEXTLINK_DANGKINGAY = Target.the("textlink Đăng kí ngay")
            .located(By.xpath("//button[contains(text(),'Đăng ký ngay')]"));
    public static final Target INPUT_FULLNAME= Target.the("input fullname")
            .located(By.xpath("//input[@placeholder='Nhập họ tên']"));
    public static final Target INPUT_SDT= Target.the("input phone")
            .located(By.xpath("//input[@placeholder='Nhập số điện thoại']"));
    public static final Target INPUT_MAIL= Target.the("input mail")
            .located(By.xpath("(//input[@placeholder='Nhập email'])[last()]"));
    public static final Target INPUT_PASSWORD= Target.the("input password")
            .located(By.xpath("(//input[contains(@placeholder, 'mật khẩu')])[last()-1]"));
    public static final Target INPUT_CONFIRM_PASS= Target.the("input confirm password")
            .located(By.xpath("(//input[contains(@placeholder, 'mật khẩu')])[last()]"));
    public static final Target BUTTON_REGISTER= Target.the("")
            .located(By.xpath("//button[@type='submit']"));
}
