package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static utils.WebDriverUtil.getOperatingSystem;

@Slf4j
public abstract class BaseAbstractPage {
    private static final SelenideElement snackBarDialog = $x("//div[@class='SnackbarItem-message']");
    private static final SelenideElement snackBarCloseBtn = $x("//button[@data-action-button='closeSnackbar' or @data-action-button='closeNotification']");

    public static void closeAllBubbles() {
        log.info("Closing all visible bubble messages");
        if (snackBarDialog.should(exist, visible).isDisplayed()) {
            snackBarCloseBtn.shouldBe(visible, enabled).click();
        }
        snackBarDialog.shouldNotBe(visible);
    }

    public static void waitTillBubbleMessageShown(String message) {
        log.info("Waiting for: {} bubble message", message);
        try {
            $x("//div[@id='notistack-snackbar' and text()='%s']".formatted(message)).shouldBe(visible);
        } catch (Exception exception) {
            log.error(snackBarDialog.getText());
        }
    }

    public static void waitTillBubbleMessagesShown(String message, String alternativeMessage) {
        log.info("Waiting for: {} bubble message", message);
        try {
            $x("//div[@id='notistack-snackbar' and text()='%s' or text()='%s']".formatted(message, alternativeMessage)).shouldBe(visible);
        } catch (Exception exception) {
            log.error(snackBarDialog.getText());
        }
    }

    public void clearInput(SelenideElement locator) {
        log.info("Input clear action");
        if (getOperatingSystem().toLowerCase().contains("mac os")) {
            locator.sendKeys(Keys.COMMAND, "A");
        } else {
            locator.sendKeys(Keys.CONTROL, "A");
        }
        locator.sendKeys(Keys.BACK_SPACE);
    }
}
