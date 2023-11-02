package page.object.portal.cases;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.hamcrest.Matchers;
import page.object.portal.models.PageCard;
import utils.AwaitUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utils.ConfirmUtil.*;

public class WorkspacePage {
    private static final String PAGE_CARD_LOC = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-name='%s' and @data-document-page-number='%d']";
    private static final String MODAL_ACTION_BOX_COLOR = "//div[contains(@class,'MuiDialogContent-root')]//div[@data-action-box-color='%s']";
    private static final String NOTES_ICON_LOC = "//i[contains(@class,'icon-notes')]";
    private static final String COLOR_ICON_BTN = "//button[.//i[contains(@class, 'icon-color')]]";
    private static final String GO_TO_INPUT_LOC = "//form[.//div[@aria-label='Go to input']]";


    private final SelenideElement episodeTypeFilterLoc = $x("//div[contains(@class,'MuiFormControl-root') and label[@id='workspaceFilterEpisodeType-label']]");
    private final SelenideElement episodeAuthorFilterLoc = $x("//div[contains(@class,'MuiFormControl-root') and label[@id='workspaceFilterEpisodeAuthor-label']]");
    private final SelenideElement episodeTypeListBoxLoc = $x("//ul[@id='workspaceFilterEpisodeType-listbox']");
    private final SelenideElement episodeAuthorListBoxLoc = $x("//ul[@id='workspaceFilterEpisodeAuthor-listbox']");
    private final SelenideElement goToPageInput = $x(GO_TO_INPUT_LOC + "//input");
    private final SelenideElement goToPageBtn = $x(GO_TO_INPUT_LOC + "//button[contains(text(),'Go to page')]");
    private final SelenideElement modalViewLoc = $x("//h2[contains(@class,'MuiDialogTitle-root')]//h6[contains(text(),'.pdf')]");
    private final SelenideElement pageColorConfirmDialog = $x("//div[contains(@role,'dialog') and .//text()='Set page colors']");
    private final SelenideElement saveColorButton = $x("//button[contains(text(),'Save')]");
    private final SelenideElement selectedColorLoc = $x("//div[contains(@class,'bg') and .//i[contains(@class,'icon-done')]]");
    private final ElementsCollection selectedColorElements = $$x("//div[contains(@class,'bg') and .//i[contains(@class,'icon-done')]]");
    private final ElementsCollection pagesCardLoc = $$x("//div[contains(@class,'card-page-container')]//div[contains(@class,'card-page-wrapper')]");





    public ModalPage openPageCardInWorkspace(String title, int pageNumber) {
        goToPage(pageNumber);
        $x(PAGE_CARD_LOC.formatted(title, pageNumber)).shouldBe(visible).click();
        return new ModalPage();
    }

    public String getModalViewDocumentTitle() {
        return modalViewLoc.shouldBe(visible).getText();
    }

    public boolean isNotesIconAppearedOnPageCard(String title, int pageNumber) {
        return $x(PAGE_CARD_LOC.formatted(title, pageNumber) + NOTES_ICON_LOC).isDisplayed();
    }

    public ModalPage setModalPageColor(String color){
        unSetPageColors();
        $x(MODAL_ACTION_BOX_COLOR.formatted(color)).shouldBe(visible).click();
        waitTillBubbleMessagesShown("Page colors was updated", "Page colors was removed");
        closeAllBubbles();
        return new ModalPage();
    }

    public WorkspacePage setPageColor(String color){
        unSetPageColors();
        pageColorConfirmDialog.$x(".//div[@data-action-box-color='%s']".formatted(color)).click();
        saveColorButton.shouldBe(enabled).click();
        waitTillBubbleMessagesShown("Page colors was updated", "Page colors was removed");
        closeAllBubbles();
        return this;
    }

    public WorkspacePage unSetPageColors() {
        AwaitUtil.awaitSafe(Duration.ofSeconds(4), Duration.ofSeconds(2), selectedColorElements::isEmpty, Matchers.is(false));
        while (!selectedColorElements.isEmpty()) {
            actions().moveToElement(selectedColorLoc).click().perform();
           sleep(1000);
        }
        return this;
    }

    public WorkspacePage openPageColorDialog(String title, int pageNum){
        $x(PAGE_CARD_LOC.formatted(title,pageNum) + COLOR_ICON_BTN).shouldBe(visible).click();
        pageColorConfirmDialog.should(appear);
        return this;
    }

    public String getModalPageColor(){
        return selectedColorLoc.should(exist).getAttribute("data-action-box-color");
    }

    public String getPageColor(String title, int pageNumber){
        var intermediate = Arrays.stream($x(PAGE_CARD_LOC.formatted(title, pageNumber)).$$x(".//span[contains(@class,'bg')]")
                .attributes("class").get(0).split("-")).toList();
        return intermediate.get(intermediate.size() - 1);
    }

    private void goToPage(int mainPage) {
        clearInput(goToPageInput);
        goToPageInput.shouldBe(exist).shouldBe(enabled).sendKeys(String.valueOf(mainPage));
        goToPageBtn.shouldBe(enabled).shouldBe(visible).hover().click();
    }

    public WorkspacePage filterPagesByEpisodeType(String episodeType){
        if(episodeTypeFilterLoc.$x(".//div[contains(@class,'MuiAutocomplete-tag')]").is(visible)){
            episodeTypeFilterLoc.$x(".//button[@title='Clear']").click();
        }
        episodeTypeFilterLoc.shouldBe(visible).shouldBe(enabled).click();
        episodeTypeListBoxLoc.should(appear).shouldBe(visible);
        episodeTypeListBoxLoc.$x(".//li[contains(text(),'%s')]".formatted(episodeType)).shouldBe(visible).click();
        episodeTypeFilterLoc.$x(".//div[@title='%s']".formatted(episodeType)).shouldBe(visible);
        return this;
    }

    public WorkspacePage filterPagesByAuthorType(String episodeType){
        if(episodeAuthorFilterLoc.$x(".//div[contains(@class,'MuiAutocomplete-tag')]").is(visible)){
            episodeAuthorFilterLoc.$x(".//button[@title='Clear']").click();
        }
        episodeAuthorFilterLoc.shouldBe(visible).shouldBe(enabled).click();
        episodeAuthorListBoxLoc.should(appear).shouldBe(visible);
        episodeAuthorListBoxLoc.$x(".//li[contains(text(),'%s')]".formatted(episodeType)).shouldBe(visible).click();
        episodeAuthorFilterLoc.$x(".//div[@title='%s']".formatted(episodeType)).shouldBe(visible);
        return this;
    }

    public List<PageCard> parseWorkspacePages() {
        List<PageCard> pageCards = new ArrayList<>();
        pagesCardLoc.shouldHave(CollectionCondition.sizeGreaterThan(0)).asFixedIterable().forEach(item -> {
            var title = item.$x(".//div").scrollIntoView(false).getAttribute("data-document-name");
            var pageNum = item.$x(".//div[contains(@class,'cp-head')]//span[contains(@class,'cp-text flex')]")
                    .getText().replaceFirst("Page", "").trim();
            var documentDateAndTime = item.$x(".//div[contains(@class,'cp-head')]//span[@class='cp-text']").getText();
            var date = documentDateAndTime.equals("Set date") ? null : documentDateAndTime.substring(0, 10).trim();
            var time = documentDateAndTime.length() > 10 ? documentDateAndTime.replace(date,"").trim() : null;
            var maybeDocumentTag = item.$x(".//span[contains(@class,'MuiChip-label')]").getText();
            var tag = maybeDocumentTag.equals("Set tag") ? null : maybeDocumentTag;
            var pageCard = PageCard.builder()
                    .documentTitle(title).pageNumber(pageNum).date(date).time(time).tag(tag).build();
            pageCards.add(pageCard);
        });
        return pageCards;
    }
}