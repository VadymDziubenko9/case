package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static utils.WebDriverManager.getOperatingSystem;
@Slf4j
public abstract class BaseAbstractPage {
    private static final SelenideElement snackBarDialog = $x("//div[@class='SnackbarItem-message']");
    private static final SelenideElement snackBarCloseBtn = $x("//button[@data-action-button='closeSnackbar' or @data-action-button='closeNotification']");
    private static final String CONFIRMATION_DIALOG = "//div[contains(@class, 'MuiPaper-root') and .//*[normalize-space()='%s']]";


    @Step("Clear input")
    public void clearInput(SelenideElement locator) {
        log.info("Input clear action");
        if (getOperatingSystem().toLowerCase().contains("mac os")) {
            locator.sendKeys(Keys.COMMAND, "A");
        } else {
            locator.sendKeys(Keys.CONTROL, "A");
        }
        locator.sendKeys(Keys.BACK_SPACE);
    }

    public static void closeAllBubbles() {
        log.info("Closing all visible bubble messages");
        if (snackBarDialog.should(exist).isDisplayed()) {
            snackBarCloseBtn.shouldBe(enabled).click();
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

    public static void confirmAction(String message) {
        $x(CONFIRMATION_DIALOG.formatted(message)).shouldBe(visible);
        $x(CONFIRMATION_DIALOG.formatted(message) + "//button[normalize-space()='Confirm']").shouldBe(visible).click();
    }

}
