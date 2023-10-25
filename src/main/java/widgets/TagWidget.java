package widgets;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static utils.ConfirmationsUtil.*;

@Slf4j
@UtilityClass
public class TagWidget {
    private final String PAGE_LOC_BY_NUMBER = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d']";
    private final String PAGE_CARD_SET_TAG_BUTTON = "//div[contains(@class,'cp-head')]//div[@role='button']";
    private final String SELECTED_TAG_VIEW = ".//div[@data-action-chip='%s']//..//div[@data-active-chip='true']";
    private final String TAG_ACTION = ".//div[@data-action-chip='%s']";
    private final String GET_SELECTED_TAG_VALUE = ".//div[@data-active-chip='true']";
    private final String UPDATED_CONFIRM = "Page tag was updated";
    private final String REMOVED_CONFIRM = "Page tag was removed";

    private final SelenideElement modalViewContainerLoc = $x("//div[contains(@class,'MuiDialogContent-root')]");
    private final SelenideElement pageTagConfirmDialog = $x("//div[contains(@role,'dialog') and .//text()='Set page tag']");
    private final SelenideElement saveTagButton = $x("//button[contains(text(),'Save')]");

    @Step("Set tag {1} to the page {0} on file view")
    public void setPageTag(int pageNum, String tag) {
        if (!getDocumentPageTag(pageNum).equals("Set tag")) {
            untagPage(pageNum);
        }
        openPageTagPopup(pageNum);
        selectTag(tag);
    }

    @Step("Open file view tag popup for page {0}")
    public void openPageTagPopup(int pageNum) {
        log.info("Opening page tag dialog on File view");
        $x(PAGE_LOC_BY_NUMBER.formatted(pageNum) + PAGE_CARD_SET_TAG_BUTTON)
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
    public void setPageTagOnModalView(String tag) {
        log.info("Setting {} page tag on Modal view", tag);
        modalViewContainerLoc.$x(TAG_ACTION.formatted(tag)).should(enabled).shouldBe(visible).click();
        modalViewContainerLoc.$x(SELECTED_TAG_VIEW.formatted(tag)).should(appear).shouldBe(visible);
        waitTillBubbleMessagesShown(UPDATED_CONFIRM, REMOVED_CONFIRM);
        closeAllBubbles();
    }

    public String getDocumentPageTag(int page) {
        return $x(PAGE_LOC_BY_NUMBER.formatted(page) + PAGE_CARD_SET_TAG_BUTTON).should(appear).getText();
    }

    public String getPageTag() {
        var item = modalViewContainerLoc.$x(GET_SELECTED_TAG_VALUE);
        if (modalViewContainerLoc.$x(GET_SELECTED_TAG_VALUE).isDisplayed()) {
            return item.getText();
        } else return null;
    }

    @Step("Remove tag from page {0}")
    public void untagPage(int page) {
        log.info("Remove tag from page #{}", page);
        if (modalViewContainerLoc.isDisplayed()) {
            $x(GET_SELECTED_TAG_VALUE).shouldBe(enabled).click();
        } else {
            if ($x(PAGE_LOC_BY_NUMBER.formatted(page) + PAGE_CARD_SET_TAG_BUTTON).isDisplayed()) {
                openPageTagPopup(page);
                pageTagConfirmDialog.$x(GET_SELECTED_TAG_VALUE).shouldBe(enabled).click();
                saveTagButton.click();
            }
        }
        waitTillBubbleMessagesShown(UPDATED_CONFIRM, REMOVED_CONFIRM);
        closeAllBubbles();
    }
}

