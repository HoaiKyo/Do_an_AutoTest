package screenplay.tasks.customer;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import screenplay.ui.customer.AppointmentSuccess;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class VerifyAppointmentInHistory implements Task {

    private final String expectedService;
    private final String expectedDateTime;

    public VerifyAppointmentInHistory(String expectedService) {
        this.expectedService = expectedService;
        this.expectedDateTime = null;
    }

    public VerifyAppointmentInHistory(String expectedService, String expectedDateTime) {
        this.expectedService = expectedService;
        this.expectedDateTime = expectedDateTime;
    }

    public static VerifyAppointmentInHistory withService(String expectedService) {
        return instrumented(VerifyAppointmentInHistory.class, expectedService);
    }

    public static VerifyAppointmentInHistory withDetails(String expectedService, String expectedDateTime) {
        return instrumented(VerifyAppointmentInHistory.class, expectedService, expectedDateTime);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Chờ cho danh sách lịch sử hiển thị
        actor.attemptsTo(
                WaitUntil.the(AppointmentSuccess.APPOINTMENT_ITEM, isVisible())
        );

        String service1 = actor.recall("service1");
        String service2 = actor.recall("service2");

        if (service1 != null && service1.contains(" (")) {
            service1 = service1.substring(0, service1.indexOf(" (")).trim();
        }
        if (service2 != null && service2.contains(" (")) {
            service2 = service2.substring(0, service2.indexOf(" (")).trim();
        }

        String cleanDateTime = expectedDateTime;
        if (cleanDateTime != null && cleanDateTime.contains(" (")) {
            int startIdx = cleanDateTime.indexOf(" (");
            int endIdx = cleanDateTime.indexOf(")");
            if (endIdx != -1 && endIdx > startIdx) {
                cleanDateTime = cleanDateTime.substring(0, startIdx) + cleanDateTime.substring(endIdx + 1);
            }
        }

        // Lấy tất cả các thẻ lịch hẹn trong danh sách
        org.openqa.selenium.WebDriver driver = net.serenitybdd.core.Serenity.getDriver();
        java.util.List<org.openqa.selenium.WebElement> cards = driver.findElements(org.openqa.selenium.By.xpath("//div[@class='space-y-4']/div"));

        boolean found = false;
        StringBuilder allCardsText = new StringBuilder();

        for (org.openqa.selenium.WebElement card : cards) {
            String cardText = card.getText();
            allCardsText.append("[").append(cardText.replace("\n", " ")).append("] ");

            // Kiểm tra xem thẻ này có khớp toàn bộ thông tin mong muốn không
            boolean matchService1 = false;
            if (service1 != null) {
                matchService1 = cardText.contains(service1);
            } else if (expectedService != null) {
                String cleanExpectedService = expectedService;
                if (cleanExpectedService.contains(" (")) {
                    cleanExpectedService = cleanExpectedService.substring(0, cleanExpectedService.indexOf(" (")).trim();
                }
                matchService1 = cardText.contains(cleanExpectedService);
            } else {
                matchService1 = true;
            }

            boolean matchService2 = (service2 == null) || cardText.contains(service2);
            boolean matchDateTime = (cleanDateTime == null) || cardText.contains(cleanDateTime);
            boolean matchStatus = cardText.contains("Chờ xác nhận");

            if (matchService1 && matchService2 && matchDateTime && matchStatus) {
                found = true;
                break;
            }
        }

        if (!found) {
            String expectedDetails = "Service 1: " + (service1 != null ? service1 : expectedService) + 
                                     ", Service 2: " + (service2 != null ? service2 : "null") + 
                                     ", DateTime: " + (cleanDateTime != null ? cleanDateTime : "null") + 
                                     ", Status: Chờ xác nhận";
            throw new AssertionError("Không tìm thấy lịch đặt hẹn mới tạo trong danh sách lịch sử.\n" +
                                     "Thông tin cần tìm: " + expectedDetails + "\n" +
                                     "Các thẻ thực tế quét được: " + allCardsText.toString());
        }
    }
}
