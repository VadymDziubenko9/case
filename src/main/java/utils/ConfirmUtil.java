package utils;

import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
@UtilityClass
public class ConfirmUtil {
    private static final SelenideElement snackBarDialog = $x("//div[@class='SnackbarItem-message']");
    private static final String CONFIRMATION_DIALOG = "//div[contains(@class, 'MuiPaper-root') and .//*[normalize-space()='%s']]";

    public static void closeAllBubbles() {
        log.info("Closing all visible bubble messages");
        if (snackBarDialog.should(exist).isDisplayed()) {
            $x("//button[@data-action-button='closeSnackbar' or @data-action-button='closeNotification']")
                    .shouldBe(exist)
                    .shouldBe(enabled)
                    .click();
        }
        snackBarDialog.shouldNotBe(visible);
    }

    public static void waitTillBubbleMessageShown(String message) {
        log.info("Waiting for: {} bubble message", message);
        try {
            $x("//div[@id='notistack-snackbar' and text()='%s']".formatted(message)).shouldBe(visible);
        } catch (Exception exception) {
            log.error(snackBarDialog.getText());
            exception.printStackTrace();
        }
    }

    public static void waitTillBubbleMessagesShown(String message, String alternativeMessage) {
        log.info("Waiting for: {} bubble message", message);
        try {
            $x("//div[@id='notistack-snackbar' and text()='%s' or text()='%s']".formatted(message, alternativeMessage)).shouldBe(visible);
        } catch (Exception exception) {
            log.error(snackBarDialog.getText());
            exception.printStackTrace();
        }
    }
        public static void confirmAction(String message) {
        $x(CONFIRMATION_DIALOG.formatted(message)).shouldBe(visible);
        $x(CONFIRMATION_DIALOG.formatted(message) + "//button[normalize-space()='Confirm']").shouldBe(visible).click();
    }

    public static void clearInput(SelenideElement locator) {
        while (!Objects.requireNonNull(locator.getAttribute("value")).isEmpty()) {
            actions().sendKeys(locator, Keys.BACK_SPACE).build().perform();
        }
    }
}
