package hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.annotations.Managed;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;
public class Hooks {

       @After
      public void tearDown() {
           ThucydidesWebDriverSupport.closeAllDrivers();
        }
}

