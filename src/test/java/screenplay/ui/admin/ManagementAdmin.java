package screenplay.ui.admin;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ManagementAdmin {
        public static final Target VerifyAdminManagement = Target.the("Verify Admin Management Admin Page")
                        .located(By.xpath("//button[@title='Đăng xuất']//*[name()='svg']"));
        public static final Target QuanTriAdmin = Target.the("Option Quan tri Admin")
                        .located(By.xpath("//a[contains(text(),'Quản trị Admin')]"));
        public static final Target BUTTON_NGUOIDUNG = Target.the("Option Nguoi Dung")
                        .located(By.xpath("//span[contains(text(),'Người dùng')]"));
        public static final Target BUTTON_ACTIVEUSER = Target.the("Button deactive Ngươi Dung")
                        .located(By.xpath(
                                        "//td[contains(text(),'hienbeo@gmail.com')]/following-sibling::td//button[@title='Mở khóa tài khoản']"));
        public static final Target BUTTON_LOGOUTPAGE = Target.the("Button logout trang admin duoui goc")
                        .located(By.xpath("//button[@title='Đăng xuất']//*[name()='svg']"));
}
