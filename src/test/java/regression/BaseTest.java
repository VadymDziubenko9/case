package regression;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import utils.WebDriverManager;

import java.util.List;

import static utils.WebDriverManager.getBrowserLogs;

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

    @Description("Verify console js errors")
    public void verifyJSErrorInConsole() {
        List<LogEntry> logs = getBrowserLogs();
        for (LogEntry logEntry : logs) {
            if (logEntry.getLevel().toString().equals("SEVERE")) {
                log.error("Severe error: %s".formatted(logEntry.getMessage()));
            }
        }
    }
}
