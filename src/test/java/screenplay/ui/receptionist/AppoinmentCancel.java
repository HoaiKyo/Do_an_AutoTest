package screenplay.ui.receptionist;


import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
public class AppoinmentCancel {
    public static final Target ICON_MAT_XEMCHITIET= Target.the("")
            .located(By.xpath("//tbody/tr[1]/td[contains(@class,'text-center')]//button[@title='Xem chi tiết']"));
    public static final Target BUTTON_HUYLICH= Target.the("")
            .located(By.xpath("//button[contains(text(),'Hủy lịch')]"));
    public static final Target INPUT_LYDO= Target.the("")
            .located(By.xpath("//textarea[@placeholder='Khách thay đổi ý định, báo bận, v.v']"));
    public static final Target BUTTON_XACNHANHUY= Target.the("")
            .located(By.xpath("//button[contains(text(),'Xác nhận Hủy')]"));
}
