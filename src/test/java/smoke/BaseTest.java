package smoke;

import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import utils.WebDriverManager;

@Slf4j
public abstract class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setupConfig() {
        WebDriverManager.initDriver();
    }

    @AfterClass(alwaysRun = true)
    public void closeWebDriver() {
        try {
            Selenide.closeWebDriver();
        } catch (IllegalStateException ex) {
            log.info("Browser wasn't opened!");
        }
    }
}
