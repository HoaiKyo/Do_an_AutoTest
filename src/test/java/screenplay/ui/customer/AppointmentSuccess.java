package screenplay.ui.customer;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AppointmentSuccess {
    public static final Target BUTTON_DATLICHNGAY = Target.the("Button Đặt lịch ngay")
            .located(By.xpath("//button[contains(normalize-space(), 'Đặt lịch ngay')]"));
    public static final Target NGAYHEN = Target.the("ngay hen")
            .located(By.xpath("//span[normalize-space()='Ngày hẹn']/following::input[@type='date'][1]"));
    public static final Target KHUNGGIO = Target.the("Khung gio")
            .located(By.xpath("//select[option[contains(text(),'Chọn khung giờ')]]"));
    public static final Target BUTTON_KETIEP = Target.the("Button ke tiep")
            .located(By.xpath("//button[contains(text(),'Kế tiếp')]"));
    public static final Target SERVICE_COMBOBOX = Target.the("Service combobox")
            .located(By.xpath("//select[option[contains(text(),'Chọn dịch vụ')]]"));
    public static final Target STAFF_COMBOBOX = Target.the("Staff combobox")
            .located(By.xpath("//select[@class='w-full p-3 rounded-xl border text-sm font-bold outline-none focus:ring-2 focus:ring-primary/20 disabled:opacity-50 border-gray-100 bg-gray-50 text-gray-500']"));
    public static final Target INPUT_NAME = Target.the("input ten nguoi dat lich")
            .located(By.xpath("//input[@placeholder='Nguyễn Văn A']"));
    public static final Target INPUT_SDT = Target.the("SDT dat lich")
            .located(By.xpath("//input[@placeholder='0912 345 678']"));
    public static final Target BUTTON_DATLICHHEN = Target.the("Button Dat Lich ngay sau cac buoc")
            .located(By.xpath("(//button[normalize-space()='Đặt lịch ngay'])[2]"));
    public static final Target POPUP_HOANTAT = Target.the("POpup hien sau khi dat lich thanh cong")
            .located(By.xpath("//h3[contains(text(),'Hoàn tất!')]"));
    public static final Target BUTTON_CLOSE = Target.the("BUTTON close popup")
            .located(By.xpath("//button[normalize-space()='Đóng']"));
    public static final Target BUTTON_HOSOCANHAN = Target.the("BUTTON profile personal")
            .located(By.xpath("//a[contains(text(),'Hồ sơ cá nhân')]"));
    public static final Target APPOINTMENT_ITEM = Target.the("Lịch hẹn đầu tiên trong danh sách")
            .located(By.xpath("//div[@class='space-y-4']/div[1]"));
    public static final Target BUTTON_ADD_SERVICE = Target.the("button để thêm dịch vụ")
            .located(By.xpath("//button[normalize-space()='Thêm dòng']"));
    public static final Target COMBOBOX_SERVICE2 = Target.the("combobox để thêm dịch vụ 2")
            .located(By.xpath("(//div[contains(@class,'space-y-4')]//div[contains(@class,'rounded-2xl')])[2]//select[option[contains(text(),'Chọn dịch vụ')]]"));
    public static final Target COMBOBOX_STAFF2 = Target.the("combobox chọn nhân viên thứ 2")
            .located(By.xpath("(//div[contains(@class,'space-y-4')]//div[contains(@class,'rounded-2xl')])[2]//select[option[contains(text(),'nhân viên') or contains(text(),'chuyên viên') or contains(text(),'sẵn sàng')]]"));
    public static final Target INPUT_TENNGUOIDICUNG = Target.the("tên người đi cùng với khách")
            .located(By.xpath("//input[@placeholder='Tên người đi cùng...']"));
    public static final Target INPUT_SOLUONG_NGUOIDICUNG = Target.the("Số lượng khách đi kèm ")
            .located(By.xpath("//input[@type='number']"));

}
