package utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.awaitility.Durations.ONE_SECOND;

@UtilityClass
@Slf4j
public class JsUtil {

    @SuppressWarnings("all")
    public static void waitForDomToLoad() {
        var webDriver = getWebDriver();

        if (null != webDriver) {
            AwaitUtil.awaitSafe(Duration.ofSeconds(20), ONE_SECOND,
                    () -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"),
                    Matchers.is(true));
        } else
            log.error("Failed to wait until DOM is loaded!");
    }
}
