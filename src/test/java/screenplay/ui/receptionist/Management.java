package screenplay.ui.receptionist;


import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class Management {
    public static final Target QuanLyLeTan = Target.the("Button Quan Ly Le Tan")
            .located(By.xpath("//a[contains(text(),'Quản lý Lễ tân')]"));
    public static final Target VerifyPageReceptionist = Target.the("verify page le tan")
            .located(By.xpath("//div[@class='flex items-center']"));
    public static final Target BUTTON_DANGXUAT = Target.the("Button Logout trang quan tri")
            .located(By.xpath("//button[@title='Đăng xuất']//*[name()='svg']"));
}
