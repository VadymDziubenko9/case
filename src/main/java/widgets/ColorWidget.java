package widgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import utils.AwaitUtil;

import java.time.Duration;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static page.object.portal.cases.BaseAbstractPage.closeAllBubbles;
import static page.object.portal.cases.BaseAbstractPage.waitTillBubbleMessagesShown;
import static utils.AwaitUtil.awaitSafe;

@Slf4j
@UtilityClass
public class ColorWidget {
    private final String PAGE_CARD_LOC = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-name='%s' and @data-document-page-number='%d']";
    private final String MODAL_ACTION_BOX_COLOR = "//div[contains(@class,'MuiDialogContent-root')]//div[@data-action-box-color='%s']";
    private final String COLOR_ICON_BTN = "//button[.//i[contains(@class, 'icon-color')]]";
    private final String SCROLL_TO_DIRECTION = "{behavior: \"instant\", block: \"end\", inline: \"end\"}";
    private final String UPDATED_COLOR_NOTIFICATION = "Page colors are updated";
    private final String REMOVER_COLOR_NOTIFICATION = "Page colors are removed";

    private final ElementsCollection pageCardsLoc = $$x("//div[contains(@class,'scrollListContainer')]//div[contains(@class,'card-page-wrapper')]");
    private final ElementsCollection selectedColorElements = $$x("//div[contains(@class,'bg') and .//i[contains(@class,'icon-done')]]");
    private final SelenideElement workspaceColorsFilterContainer = $x("//div[contains(@class,'MuiContainer-disableGutters')]//div[contains(@class,'MuiBox-root') and ./div[contains(@class,'bg')]]");
    private final SelenideElement pageColorConfirmDialog = $x("//div[contains(@role,'dialog') and .//text()='Set page colors']");
    private final SelenideElement saveColorButton = $x("//button[contains(text(),'Save')]");
    private final SelenideElement selectedColorLoc = $x("//div[contains(@class,'bg') and .//i[contains(@class,'icon-done')]]");


    @Step("Set page {0} color due File view")
    public void setPageColor(String color, int pageNum, String title) {
        log.info("Setting up {} page color due File view", color);
        openPageColorDialog(title, pageNum);
        unCheckPageColors();
        pageColorConfirmDialog.$x(".//div[@data-action-box-color='%s']".formatted(color)).click();
        saveColorButton.shouldBe(enabled).click();
        waitTillBubbleMessagesShown(UPDATED_COLOR_NOTIFICATION, REMOVER_COLOR_NOTIFICATION);
        closeAllBubbles();
    }

    @Step("Reset page color")
    public void unCheckPageColors() {
        log.info("Unsetting page colors");
        AwaitUtil.awaitSafe(Duration.ofSeconds(3), Duration.ofMillis(500), selectedColorElements::isEmpty, Matchers.is(false));
        while (!selectedColorElements.isEmpty()) {
            actions().moveToElement(selectedColorLoc).click().perform();
            sleep(500);
        }
    }

    @Step("Set page {0} color due Modal view")
    public void setModalPageColor(String color) {
        log.info("Setting up {} page color due Modal view", color);
        unCheckPageColors();
        $x(MODAL_ACTION_BOX_COLOR.formatted(color)).shouldBe(visible).click();
        waitTillBubbleMessagesShown(UPDATED_COLOR_NOTIFICATION, REMOVER_COLOR_NOTIFICATION);
        closeAllBubbles();
    }

    public String getPageColor(String title, int pageNumber) {
        var intermediate = Arrays.stream($x(PAGE_CARD_LOC.formatted(title, pageNumber)).$$x(".//span[contains(@class,'bg')]")
                .attributes("class").get(0).split("-")).toList();
        return intermediate.get(intermediate.size() - 1);
    }

    public String getModalPageColor() {
        return selectedColorLoc.should(exist).getAttribute("data-action-box-color");
    }

    public void openPageColorDialog(String title, int pageNum) {
        var index = 0;
        do {
            int finalIndex = index;
            awaitSafe(Duration.ofSeconds(3), Duration.ofMillis(50),
                    () -> pageCardsLoc.get(finalIndex).scrollIntoView(SCROLL_TO_DIRECTION), Matchers.is(visible));
            index++;
        } while (index < pageCardsLoc.size() && !$x(PAGE_CARD_LOC.formatted(title, pageNum)).is(enabled));
        $x(PAGE_CARD_LOC.formatted(title, pageNum) + COLOR_ICON_BTN).shouldBe(visible).click();
        pageColorConfirmDialog.should(appear);
    }

    public void filterPagesByColors(String @NonNull ... colors) {
        for (String color : colors) {
            workspaceColorsFilterContainer.shouldBe(visible).$x(".//div[@data-action-box-color='%s']".formatted(color)).shouldBe(visible).click();
        }
    }
}
