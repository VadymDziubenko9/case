package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.selenide.LogType.*;
import static java.util.logging.Level.INFO;
import static org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY;
import static org.openqa.selenium.remote.CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR;

@Slf4j
@UtilityClass
public class WebDriverManager {
    public static String openNewTab() {
        ((JavascriptExecutor) getWebDriver()).executeScript("window.open()");
        return getWebDriver().getWindowHandles().toArray()[1].toString();
    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static void clearCookies() {
        getWebDriver().manage().deleteAllCookies();
    }

    public static List<LogEntry> getBrowserLogs() {
        return getWebDriver().manage().logs().get("browser").getAll();
    }

    @Step("Driver Initialization")
    public static void initDriver() {
        log.info("Set Selenide configurations");
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .includeSelenideSteps(true)
                .enableLogs(SERVER, INFO)
                .enableLogs(CLIENT, INFO)
                .enableLogs(DRIVER, INFO)
                .enableLogs(BROWSER, INFO)
                .screenshots(true));

        Configuration.browserCapabilities.setCapability(UNHANDLED_PROMPT_BEHAVIOUR, ACCEPT_AND_NOTIFY);
        Configuration.baseUrl = "https://cases-qa.casechronology.com";
        Configuration.browserSize = "1920x1080";
        Configuration.fastSetValue = false;
        Configuration.timeout = 10_000;
        Configuration.headless = false;
        Configuration.downloadsFolder = "selenideFolder";
        Configuration.fileDownload = FOLDER;
        Configuration.proxyEnabled = false;
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--disable-notifications", "--disable-popup-blocking", "--incognito");
    }
}
