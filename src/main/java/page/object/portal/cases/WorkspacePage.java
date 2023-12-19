package page.object.portal.cases;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.PageCard;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.interactions.WheelInput.ScrollOrigin.fromElement;

@Slf4j
public class WorkspacePage extends BaseAbstractPage{
    private static final String PAGE_CARD_LOC = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-name='%s' and @data-document-page-number='%d']";
    private static final String NOTES_ICON_LOC = "//i[contains(@class,'icon-notes')]";

    private final SelenideElement modalViewLoc = $x("//h2[contains(@class,'MuiDialogTitle-root')]//h6[contains(text(),'.PDF') or contains(text(),'.pdf')]");
    private final SelenideElement totalWorkspacePages = $x("//div[contains(@class,'MuiToolbar-root')]//p[contains(text(),'Total number of pages')]/b[1]");
    private final SelenideElement totalWorkspacePagesIncludingStapled = $x("//div[contains(@class,'MuiToolbar-root')]//p[contains(normalize-space(),'Including stapled pages: ')]/b[2]");
    private final SelenideElement noPagesInWorkspaceLoc = $x("//div[contains(@class,'MuiBox-root') and ./p[contains(text(),'No page(s) in workspace')]]");

    private final ElementsCollection pageCardWrapperCollection = $$x("//div[contains(@class,'card-page-container')]//div[contains(@class,'card-page-wrapper')]");
    private final ElementsCollection pageCardCollection = $$x("//div[contains(@class,'card-page-container')]");

    @Step("Open {0} page {1} card in Workspace")
    public void openPageInWorkspace(String title, int pageNumber) {
        log.info("Opening page {} {} card in Workspace", pageNumber, title);
        var index = 0;
        while (!$x(PAGE_CARD_LOC.formatted(title, pageNumber)).isDisplayed()) {
            actions().scrollFromOrigin(fromElement(pageCardCollection.get(index)), 0, 222).perform();
            index++;
            sleep(500);
        }
        $x(PAGE_CARD_LOC.formatted(title, pageNumber)).scrollIntoView(false).shouldBe(visible).click();
    }

    public String getModalViewDocumentTitle() {
        return modalViewLoc.shouldBe(visible).getText();
    }

    public boolean isNotesIconAppearedOnPageCard(String title, int pageNumber) {
        return $x(PAGE_CARD_LOC.formatted(title, pageNumber) + NOTES_ICON_LOC).isDisplayed();
    }

    public List<PageCard> parseWorkspacePages() {
        List<PageCard> pageCards = new ArrayList<>();
        pageCardWrapperCollection.shouldHave(CollectionCondition.sizeGreaterThan(0)).asFixedIterable().forEach(item -> {
            var title = item.$x(".//div").scrollIntoView(false).getAttribute("data-document-name");
            var pageNum = item.$x(".//div[contains(@class,'cp-head')]//span[contains(@class,'cp-text flex')]")
                    .getText().replaceFirst("Page", "").trim();
            var documentDateAndTime = item.$x(".//div[contains(@class,'cp-head')]//span[@class='cp-text']").getText();
            var date = documentDateAndTime.equals("Set date") ? null : documentDateAndTime.substring(0, 10).trim();
            var time = documentDateAndTime.length() > 10 ? documentDateAndTime.replace(date, "").trim() : null;
            var maybeDocumentTag = item.$x(".//span[contains(@class,'MuiChip-label')]").getText();
            var tag = maybeDocumentTag.equals("Set tag") ? null : maybeDocumentTag;
            var pageCard = PageCard.builder()
                    .documentTitle(title).pageNumber(pageNum).date(date).time(time).tag(tag).build();
            pageCards.add(pageCard);
        });
        return pageCards;
    }

    public List<String> getListOfDisplayedDocumentsTitle() {
        return parseWorkspacePages().stream().map(PageCard::getDocumentTitle).toList();
    }

    public int getTotalNumberOfPages() {
        return Integer.parseInt(totalWorkspacePages.getText());
    }

    public int getTotalNumberOfPagesIncludingStapled() {
        return Integer.parseInt(totalWorkspacePagesIncludingStapled.getText());
    }

    public String verifyEmptyWorkspace() {
        return noPagesInWorkspaceLoc.shouldBe(visible).getText();
    }
}
