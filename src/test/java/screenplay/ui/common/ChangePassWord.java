package screenplay.ui.common;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ChangePassWord {
    public static final Target BUTTON_DOIMATKHAU = Target.the("Đổi mật khẩu button")
            .located(By.xpath("//button[contains(.,'Đổi mật khẩu')]"));
    public static final Target INPUT_MATKHAU_HIENTAI= Target.the("Mật khẩu hiện tại")
            .located(By.xpath("//label[normalize-space()='Mật khẩu hiện tại']/following-sibling::input"));
    public static final Target INPUT_MATKHAU_MOI= Target.the("Mật khẩu mới")
            .located(By.xpath("//label[normalize-space()='Mật khẩu mới']/following-sibling::input"));
    public static final Target INPUT_XACNHAN_MATKHAU= Target.the("Xác nhận mật khẩu")
            .located(By.xpath("//label[normalize-space()='Xác nhận mật khẩu']/following-sibling::input"));
    public static final Target BUTTON_LUUTHAYDOI= Target.the("")
            .located(By.xpath("//button[contains(text(),'Lưu thay đổi')]"));
}
