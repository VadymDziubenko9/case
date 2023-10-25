package utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

@Slf4j
@AllArgsConstructor
public abstract class ConfirmationsUtil {

    public static void closeAllBubbles() {
        log.info("Closing all visible bubble messages");
        if ($x("//div[@class='SnackbarItem-message']").should(exist).isDisplayed()) {
            $x("//button[@data-action-button='closeSnackbar' or @data-action-button='closeNotification']").shouldBe(exist).shouldBe(enabled).click();
        }
        $x("//div[@class='SnackbarItem-message']").shouldNotBe(visible);
    }

    public static void waitTillBubbleMessageShown(String message) {
        log.info("Waiting for: {} bubble message", message);
        $x("//div[@id='notistack-snackbar' and text()='%s']".formatted(message)).shouldBe(visible);
    }

    public static void waitTillBubbleMessagesShown(String message, String alternativeMessage) {
        log.info("Waiting for: {} bubble message", message);
        $x("//div[@id='notistack-snackbar' and text()='%s' or text()='%s']".formatted(message, alternativeMessage)).shouldBe(visible);
    }

    public static void confirmAction() {
        $x("//div[contains(@class, 'MuiPaper-root') and .//*[normalize-space()='%s']]".formatted("Delete staple from pages")).shouldBe(visible);
        $x("//div[contains(@class, 'MuiPaper-root') and .//*[normalize-space()='%s']]//button[normalize-space()='Confirm']"
                .formatted("Delete staple from pages"))
                .shouldBe(visible).click();
    }
}
