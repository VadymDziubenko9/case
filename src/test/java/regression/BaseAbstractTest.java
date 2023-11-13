package regression;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import page.object.portal.cases.*;
import utils.WebDriverManager;

import java.util.List;

import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;
import static utils.WebDriverManager.getBrowserLogs;

@Slf4j
public abstract class BaseAbstractTest {
    protected static final String COPIED_CASE_NAME = "Copy of John Smith";
    protected static final ModalPage modalPage = new ModalPage();
    protected static final WorkspacePage workspacePage = new WorkspacePage();
    protected static int MAIN_STAPLE_PAGE;
    protected static String DOCUMENT_TITLE;
    protected final HomePage homePage = new HomePage();

    @BeforeSuite(alwaysRun = true)
    public void setupConfig() {
        WebDriverManager.initDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void deleteCase() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
        homePage
                .archiveCase(COPIED_CASE_NAME)
                .openArchivedTab()
                .deleteCase(COPIED_CASE_NAME);
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