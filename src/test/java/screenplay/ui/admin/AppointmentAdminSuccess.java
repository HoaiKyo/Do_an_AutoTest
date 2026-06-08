package screenplay.ui.admin;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AppointmentAdminSuccess {

    public static final Target INPUT_NAME = Target.the("Nhap ten khach")
            .located(By.xpath("//input[@placeholder='Nhập tên khách hàng']"));

    public static final Target INPUT_SDT = Target.the("Nhap so dien thoai khach")
            .located(By.xpath("//input[@placeholder='Ví dụ: 0901234567']"));

    public static final Target COMBOBOX_SERVICE = Target.the("dich vu dong 1")
            .located(By.cssSelector(
                    "aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(1) select:nth-of-type(2)"
            ));

    public static final Target COMBOBOX_CHUYENVIEN = Target.the("chuyen vien dong 1")
            .located(By.cssSelector(
                    "aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(1) select:last-of-type"
            ));

    public static final Target INPUT_NGAYHEN = Target.the("ngay hen")
            .located(By.xpath("//input[@type='date']"));

    public static final Target INPUT_GIOHEN = Target.the("gio hen")
            .located(By.xpath("//label[contains(normalize-space(.),'Giờ bắt đầu')]/following-sibling::select"));

    public static final Target APPOINTMENT_ITEM_ADMIN = Target.the("item lich dat ben admin")
            .located(By.xpath("//tbody/tr[1]"));

    public static final Target BUTTON_LICHHEN_MENU = Target.the("Menu Lich hen")
            .located(By.xpath("//span[contains(text(),'Lịch hẹn')] | //a[contains(text(),'Lịch hẹn')]"));

    public static final Target BUTTON_TAOLICHHEN = Target.the("Button Tao lich hen")
            .located(By.xpath("//button[contains(normalize-space(),'Tạo lịch hẹn')] | //a[contains(normalize-space(),'Tạo lịch hẹn')]"));

    public static final Target BUTTON_LUULICHHEN = Target.the("Button Luu lich hen trong panel")
            .located(By.cssSelector("aside.admin-slide-in-right button.admin-btn.admin-btn-primary"));

    public static final By MODAL_CREATE_FORM_INPUT = By.xpath("//input[@placeholder='Nhập tên khách hàng']");

    public static final Target BUTTON_THEMNGUOI = Target.the("Them khach di kem")
            .located(By.xpath("//button[contains(normalize-space(.),'+ Thêm người')]"));

    public static final Target INPUT_TENKHACHDIKEM = Target.the("ten khach di kem")
            .located(By.xpath("//input[@placeholder='Tên khách đi kèm']"));

    public static final Target COMBOBOX_SERVICE_2 = Target.the("dich vu dong 2")
            .located(By.cssSelector(
                    "aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(2) select:nth-of-type(2)"
            ));

    public static final Target COMBOBOX_STAFF_2 = Target.the("chuyen vien dong 2")
            .located(By.cssSelector(
                    "aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(2) select:last-of-type"
            ));

    public static final Target COMBOBOX_TENKHACHDIKEM_ROW2 = Target.the("khach dong 2")
            .located(By.cssSelector(
                    "aside.admin-slide-in-right div.rounded-lg.border.p-2\\.5:nth-of-type(2) select:nth-of-type(1)"
            ));

    // Locators specifically for TC_APT_06 (or future cases with different structure)
    public static final Target COMBOBOX_SERVICE_ROW1_TC06 = Target.the("dich vu dong 1 TC06")
            .located(By.xpath("(//aside[contains(@class,'admin-slide-in-right')]//div[contains(@class,'rounded-lg') and contains(@class,'p-2.5') and contains(@class,'space-y-2')]//select[2])[1]"));

    public static final Target COMBOBOX_CHUYENVIEN_ROW1_TC06 = Target.the("chuyen vien dong 1 TC06")
            .located(By.xpath("(//aside[contains(@class,'admin-slide-in-right')]//div[contains(@class,'rounded-lg') and contains(@class,'p-2.5') and contains(@class,'space-y-2')]//select[3])[1]"));

    public static final Target COMBOBOX_SERVICE_ROW2_TC06 = Target.the("dich vu dong 2 TC06")
            .located(By.xpath("(//aside[contains(@class,'admin-slide-in-right')]//div[contains(@class,'rounded-lg') and contains(@class,'p-2.5') and contains(@class,'space-y-2')]//select[2])[2]"));

    public static final Target COMBOBOX_STAFF_ROW2_TC06 = Target.the("chuyen vien dong 2 TC06")
            .located(By.xpath("(//aside[contains(@class,'admin-slide-in-right')]//div[contains(@class,'rounded-lg') and contains(@class,'p-2.5') and contains(@class,'space-y-2')]//select[3])[2]"));

    public static final Target COMBOBOX_TENKHACHDIKEM_ROW2_TC06 = Target.the("khach dong 2 TC06")
            .located(By.xpath("(//aside[contains(@class,'admin-slide-in-right')]//div[contains(@class,'rounded-lg') and contains(@class,'p-2.5') and contains(@class,'space-y-2')]//select[1])[2]"));
}
