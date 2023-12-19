package widgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import page.object.portal.cases.BaseAbstractPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.interactions.WheelInput.ScrollOrigin.fromElement;

@Slf4j
@UtilityClass
public class TagWidget extends BaseAbstractPage {
    private final String PAGE_LOC_BY_NUMBER = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-name='%s' and @data-document-page-number='%d']";
    private final String PAGE_CARD_SET_TAG_BUTTON = "//div[contains(@class,'cp-head')]//div[@role='button']";
    private final String SELECTED_TAG_VIEW = ".//div[@data-action-chip='%s']//..//div[@data-active-chip='true']";
    private final String TAG_ACTION = ".//div[@data-action-chip='%s']";
    private final String ACTIVE_WORKSPACE_TAG_FILTER = "//div[contains(@class,'MuiChip-root') and @data-action-chip='%s' and @data-active-chip='true']";
    private final String UPDATED_CONFIRM = "Page tag is updated";
    private final String REMOVED_CONFIRM = "Page tag is removed";
    private final String PAGE_CARD_LOC = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-name='%s' and @data-document-page-number='%d']";

    private final SelenideElement modalViewContainerLoc = $x("//div[contains(@class,'MuiDialogContent-root')]");
    private final SelenideElement pageTagConfirmDialog = $x("//div[contains(@role,'dialog') and .//text()='Set page tag']");
    private final SelenideElement saveTagButton = $x("//button[contains(text(),'Save')]");
    private final SelenideElement anySelectedTagLoc = $x("//div[@data-active-chip='true']");
    private final SelenideElement workspaceTagsFilterContainer = $x("//div[contains(@class,'MuiContainer-disableGutters')]//div[contains(@class,'MuiBox-root') and ./div[contains(@class,'MuiChip-root')]]");

    private final ElementsCollection pageCardCollection = $$x("//div[contains(@class,'card-page-container')]");

    @Step("Set tag {1} to the page {0} on file view")
    public void setPageTag(int pageNum, String tag, String title) {
        scrollToPage(title, pageNum);
        if (!getPageTagInWorkspace(title, pageNum).equals("Set tag")) {
            untagPage(title, pageNum);
        }
        openPageTagPopup(title, pageNum);
        selectTag(tag);
    }

    public void scrollToPage(String title, int pageNumber) {
        log.info("Opening page {} {} card in Workspace", pageNumber, title);
        var index = 0;
        while (!$x(PAGE_CARD_LOC.formatted(title, pageNumber)).isDisplayed()) {
            actions().scrollFromOrigin(fromElement(pageCardCollection.get(index)), 0, 222).perform();
            index++;
            sleep(500);
        }
        $x(PAGE_CARD_LOC.formatted(title, pageNumber)).scrollIntoView(false).shouldBe(visible);
    }

    @Step("Open file view tag popup for page {0}")
    public void openPageTagPopup(String title, int pageNum) {
        log.info("Opening page tag dialog on File view");
        $x(PAGE_LOC_BY_NUMBER.formatted(title, pageNum) + PAGE_CARD_SET_TAG_BUTTON)
                .scrollIntoView(false)
                .shouldBe(enabled).click();
        pageTagConfirmDialog.should(appear).shouldBe(visible);
    }

    @Step("Select tag {0}")
    public void selectTag(String tag) {
        log.info("Setting {} page tag on File view", tag);
        pageTagConfirmDialog.$x(TAG_ACTION.formatted(tag)).should(enabled).shouldBe(visible).click();
        pageTagConfirmDialog.$x(SELECTED_TAG_VIEW.formatted(tag)).should(appear).shouldBe(visible);
        saveTagButton.click();
        waitTillBubbleMessagesShown(UPDATED_CONFIRM, REMOVED_CONFIRM);
        closeAllBubbles();
    }

    @Step("Set tag {0} on modal view")
    public static void setPageTagOnModalView(String tag) {
        log.info("Setting {} page tag on Modal view", tag);
        unselectTagIfSelected();
        modalViewContainerLoc.$x(TAG_ACTION.formatted(tag)).shouldHave(visible).hover().click();
        modalViewContainerLoc.$x(SELECTED_TAG_VIEW.formatted(tag)).shouldHave(visible);
        waitTillBubbleMessagesShown(UPDATED_CONFIRM, REMOVED_CONFIRM);
        closeAllBubbles();
    }

    private void unselectTagIfSelected() {
        if (anySelectedTagLoc.is(visible)) {
            anySelectedTagLoc.shouldBe(enabled).click();
            sleep(500);
        }
    }

    public String getPageTagInWorkspace(String title, int page) {
        return $x(PAGE_LOC_BY_NUMBER.formatted(title, page) + PAGE_CARD_SET_TAG_BUTTON).should(appear).getText();
    }

    public String getPageTagInModalView() {
        return anySelectedTagLoc.getAttribute("data-action-chip");
    }

    @Step("Remove tag from page {0}")
    public void untagPage(String title, int page) {
        log.info("Remove tag from page #{}", page);
        if ($x(PAGE_LOC_BY_NUMBER.formatted(title, page) + PAGE_CARD_SET_TAG_BUTTON).isDisplayed()) {
            openPageTagPopup(title, page);
            anySelectedTagLoc.shouldBe(enabled).click();
            saveTagButton.click();
            waitTillBubbleMessagesShown(UPDATED_CONFIRM, REMOVED_CONFIRM);
            closeAllBubbles();
        }
    }

    public static void filterPagesByTags(String @NonNull ... tags) {
        for (String tag : tags) {
            workspaceTagsFilterContainer.shouldBe(visible).$x(".//div[@data-action-chip='%s']".formatted(tag.toLowerCase()))
                    .shouldBe(visible)
                    .click();
            $x(ACTIVE_WORKSPACE_TAG_FILTER.formatted(tag.toLowerCase())).shouldBe(appear).shouldBe(visible);
        }
    }
}

