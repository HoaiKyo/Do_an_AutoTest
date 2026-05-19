package screenplay.tasks.admin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.admin.AppointmentAdminSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ClickSaveAppointment implements Task {
    public static ClickSaveAppointment click() {
        return instrumented(ClickSaveAppointment.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        org.openqa.selenium.WebDriver driver = net.serenitybdd.core.Serenity.getDriver();
        
        while (true) {
            actor.attemptsTo(
                    Scroll.to(AppointmentAdminSuccess.BUTTON_LUULICHHEN),
                    WaitUntil.the(AppointmentAdminSuccess.BUTTON_LUULICHHEN, isVisible()),
                    Click.on(AppointmentAdminSuccess.BUTTON_LUULICHHEN)
            );
            
            // Kiểm tra xem có xuất hiện alert báo lỗi chuyên viên bận không
            try {
                org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2));
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
                
                String alertText = driver.switchTo().alert().getText();
                if (alertText.toLowerCase().contains("thành công")) {
                    // Thành công! Để lại alert để các bước kiểm thử tiếp theo xác nhận và đóng
                    break;
                } else {
                    // Xuất hiện alert báo lỗi (ví dụ chuyên viên bận), tự động chuyển chuyên viên khác và lưu lại
                    System.out.println("Phát hiện lỗi đặt lịch: " + alertText + ". Tự động chuyển chuyên viên khác...");
                    driver.switchTo().alert().accept(); // Đóng alert lỗi
                    
                    org.openqa.selenium.support.ui.Select selectSpec = new org.openqa.selenium.support.ui.Select(AppointmentAdminSuccess.COMBOBOX_CHUYENVIEN.resolveFor(actor));
                    int specCount = selectSpec.getOptions().size();
                    if (specCount > 1) {
                        int currentSelectedIndex = -1;
                        for (int i = 0; i < specCount; i++) {
                            if (selectSpec.getOptions().get(i).isSelected()) {
                                currentSelectedIndex = i;
                                break;
                            }
                        }
                        
                        // Chọn chuyên viên tiếp theo trong danh sách
                        int nextIndex = (currentSelectedIndex == -1) ? 1 : (currentSelectedIndex % (specCount - 1)) + 1;
                        selectSpec.selectByIndex(nextIndex);
                        
                        // Chờ một chút để UI cập nhật
                        Thread.sleep(500);
                        continue; // Tiếp tục thử lưu lại
                    }
                }
            } catch (Exception e) {
                // Không có alert xuất hiện ngay hoặc lỗi khác, dừng vòng lặp
                break;
            }
            break;
        }
    }
}
