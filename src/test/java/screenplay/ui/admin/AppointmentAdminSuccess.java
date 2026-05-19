package screenplay.ui.admin;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AppointmentAdminSuccess {

    public static final Target INPUT_NAME = Target.the("Nhập tên khách")
            .located(By.xpath("//input[@placeholder='Nhập tên khách hàng']"));
    public static final Target INPUT_SDT= Target.the("Nhập số điện thoại khách")
            .located(By.xpath("//input[@placeholder='Ví dụ: 0901234567']"));
    public static final Target COMBOBOX_SERVICE= Target.the("dịch vụ")
            .located(By.xpath("//label[contains(text(),'Dịch vụ') or contains(text(),'dịch vụ')]/following-sibling::select | (//select[@class='w-full px-3 py-2 rounded-lg border text-sm'])[2]"));
    public static final Target BUTTON_ADD_SERVICE_ROW = Target.the("Nút thêm dòng dịch vụ")
            .located(By.xpath("//button[contains(text(),'+ Thêm dòng dịch vụ')]"));
    public static final Target COMBOBOX_CHUYENVIEN= Target.the("chuyên viên")
            .located(By.xpath("//label[contains(text(),'Chuyên viên') or contains(text(),'chuyên viên')]/following-sibling::select | (//select[@class='w-full px-3 py-2 rounded-lg border text-sm'])[3]"));
    public static final Target INPUT_NGAYHEN= Target.the("ngày hẹn")
            .located(By.xpath("//input[@type='date']"));
    public static final Target INPUT_GIOHEN= Target.the("giờ hẹn")
            .located(By.xpath("//label[contains(text(),'Giờ bắt đầu')]/following-sibling::select"));
    public static final Target APPOINTMENT_ITEM_ADMIN = Target.the("item lich dat ben admin")
            .located(By.xpath("//tbody/tr[1]"));
    public static final Target BUTTON_LICHHEN_MENU = Target.the("Menu Lịch hẹn")
            .located(By.xpath("//span[contains(text(),'Lịch hẹn')] | //a[contains(text(),'Lịch hẹn')]"));
    public static final Target BUTTON_TAOLICHHEN = Target.the("Button Tạo lịch hẹn")
            .located(By.xpath("//button[contains(normalize-space(),'Tạo lịch hẹn')] | //a[contains(normalize-space(),'Tạo lịch hẹn')]"));
    public static final Target BUTTON_LUULICHHEN = Target.the("Button Lưu lịch hẹn")
            .located(By.xpath("//button[contains(text(),'Lưu lịch hẹn')] | //button[contains(.,'Lưu lịch hẹn')] | //button[contains(normalize-space(),'Lưu')]"));

}
