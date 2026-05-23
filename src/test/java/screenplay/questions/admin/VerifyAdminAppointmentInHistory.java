package screenplay.questions.admin;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class VerifyAdminAppointmentInHistory implements Question<Boolean> {

    public static VerifyAdminAppointmentInHistory withDetails() {
        return new VerifyAdminAppointmentInHistory();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String expectedName = actor.recall("admin_customer_name");
        String expectedPhone = actor.recall("admin_customer_phone");
        String expectedService = actor.recall("admin_service");
        String expectedDate = actor.recall("admin_date");
        String expectedTime = actor.recall("admin_time");

        System.out.println("=== DEBUG ACTOR MEMORY ===");
        System.out.println("Expected Name: " + expectedName);
        System.out.println("Expected Phone: " + expectedPhone);
        System.out.println("Expected Service: " + expectedService);
        System.out.println("Expected Date: " + expectedDate);
        System.out.println("Expected Time: " + expectedTime);
        System.out.println("==========================");

        // Clean expected service text (strip cost and other suffixes)
        if (expectedService != null) {
            expectedService = expectedService.split("\\s*[\\(-–—]\\s*")[0].trim();
        }

        // Clean expected time text (strip capacity details)
        if (expectedTime != null && expectedTime.contains(" (")) {
            int startIdx = expectedTime.indexOf(" (");
            int endIdx = expectedTime.indexOf(")");
            if (endIdx != -1 && endIdx > startIdx) {
                expectedTime = expectedTime.substring(0, startIdx) + expectedTime.substring(endIdx + 1);
            }
        }

        final String finalExpectedService = expectedService;
        final String finalExpectedTime = expectedTime;
        final String finalExpectedName = expectedName;
        final String finalExpectedDate = expectedDate;

        try {
            WebDriver driver = Serenity.getDriver();
            driver.navigate().refresh(); // Refresh the browser page to force load the latest data from the backend
            
            // Click chọn tab "Tất cả" để hiển thị cả các lịch hẹn ngày mai (ngày tạo mặc định trong test là ngày mai)
            try {
                WebDriverWait waitBtn = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement tatCaBtn = waitBtn.until(d -> d.findElement(By.xpath("//button[text()='Tất cả']")));
                tatCaBtn.click();
            } catch (Exception ignored) {}

            // Đợi table xuất hiện trong DOM rồi cuộn đến nó (tự động hoạt động với mọi container có scrollbar)
            try {
                WebDriverWait waitTable = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement table = waitTable.until(d -> d.findElement(By.xpath("//table | //tbody")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", table);
            } catch (Exception ignored) {}
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Poll the table until a row matching all expected details appears (traversing pages if necessary)
            Boolean matched = wait.until(d -> {
                try {
                    while (true) {
                        List<WebElement> rows = d.findElements(By.xpath("//tbody/tr"));
                        for (WebElement row : rows) {
                            String rowText = row.getText();
                            
                            boolean matchName = (finalExpectedName == null) || rowText.contains(finalExpectedName);
                            boolean matchPhone = true;
                            boolean matchService = (finalExpectedService == null) || rowText.contains(finalExpectedService);
                            boolean matchDate = (finalExpectedDate == null) || rowText.contains(finalExpectedDate);
                            boolean matchTime = (finalExpectedTime == null) || rowText.contains(finalExpectedTime);
                            boolean matchStatus = rowText.contains("Chờ xác nhận"); // Chỉ quét lịch hẹn ở trạng thái Chờ xác nhận

                            if (matchName && matchPhone && matchService && matchDate && matchTime && matchStatus) {
                                return true;
                            }
                        }
                        
                        // Thử chuyển trang tiếp theo nếu không tìm thấy ở trang hiện tại
                        List<WebElement> nextButtons = d.findElements(By.xpath(
                            "//button[contains(text(), 'Sau') or contains(text(), 'Next') or contains(., '>') or @aria-label='Next page']"
                        ));
                        
                        WebElement nextBtn = null;
                        for (WebElement btn : nextButtons) {
                            if (btn.isDisplayed() && btn.isEnabled()) {
                                String className = btn.getAttribute("class");
                                String disabledAttr = btn.getAttribute("disabled");
                                if ((className == null || (!className.contains("disabled") && !className.contains("opacity-50"))) 
                                    && disabledAttr == null) {
                                    nextBtn = btn;
                                    break;
                                }
                            }
                        }
                        
                        if (nextBtn != null) {
                            String beforeText = d.findElement(By.xpath("//tbody/tr[1]")).getText();
                            nextBtn.click();
                            
                            // Đợi trang mới được load (dòng đầu tiên đổi nội dung)
                            new WebDriverWait(d, Duration.ofSeconds(3))
                                .until(dr -> !dr.findElement(By.xpath("//tbody/tr[1]")).getText().equals(beforeText));
                            continue; // Quét trang tiếp theo
                        }
                        break; // Hết trang để quét
                    }
                } catch (Exception ignored) {}
                return null; // Tiếp tục poll nếu chưa hết thời gian chờ
            });
            return matched != null && matched;
        } catch (Exception e) {
            // Lấy toàn bộ text của bảng để xuất ra màn hình phục vụ debug
            StringBuilder debugInfo = new StringBuilder();
            try {
                WebDriver driver = Serenity.getDriver();
                List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
                for (int i = 0; i < rows.size(); i++) {
                    debugInfo.append("\n  Row ").append(i + 1).append(": ").append(rows.get(i).getText().replace("\n", " | "));
                }
            } catch (Exception ignored) {}

            throw new AssertionError("Không tìm thấy lịch hẹn trùng khớp sau 10 giây!\n" +
                    "  [Mong đợi] Name: " + finalExpectedName + "\n" +
                    "  [Mong đợi] Service: " + finalExpectedService + "\n" +
                    "  [Mong đợi] Date: " + finalExpectedDate + "\n" +
                    "  [Mong đợi] Time: " + finalExpectedTime + "\n" +
                    "  [Danh sách thực tế quét được]:" + debugInfo.toString());
    }
}}
