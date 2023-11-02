package page.object.portal.cases;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import page.object.portal.models.Document;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static enums.ProcessingStatus.READY;
import static utils.Config.BASE_URL;
import static utils.ConfirmUtil.*;

@Slf4j
public class HomePage {
    private static final String ACTION_BUTTON = "//button[@data-action-button='%s']";
    private static final String SCROLL_TO_PARAMETER = "{block: \"center\", inline: \"start\"}";
    private static final String SELECTED_CASE_TAB_LOC = "//a[contains(@href,'%s') and @aria-selected='true']";
    private static final String PAGE_LOC_BY_NUMBER = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d']";
    private static final String ADD_PARTICULAR_PAGE_TO_STAPLE = "//button[.//i[contains(@class,'icon-add')]]";
    private static final String PARTICULAR_PAGE_IN_STAPLE = "//button[.//i[contains(@class,'icon-done')]]";
    private static final String CLOSE_PAGE_CARD_PREVIEW_BUTTON = ".//button[.//*[@data-testid='CloseIcon']]";
    private static final String GO_TO_INPUT_LOC = "//form[.//div[@aria-label='Go to input']]";
    private static final String FIRST_PAGE_IN_STAPLE_BY_PAGE_NUM = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d' and @data-page-staple-order='1']";

    private final SelenideElement goToPageInput = $x(GO_TO_INPUT_LOC + "//input");
    private final SelenideElement goToPageBtn = $x(GO_TO_INPUT_LOC + "//button[contains(text(),'Go to page')]");
    private final SelenideElement searchInput = $x("//div[@data-control-input='search']//input");
    private final SelenideElement caseContextMenuButton = $x("//button[contains(@data-action-button,'caseDropdown')]");
    private final SelenideElement caseContextMenuList = $x("//ul[contains(@class,'MuiList-root') and @role='menu']");
    private final SelenideElement caseDuplicateDialog = $x("//form[contains(@role,'dialog') and .//text()='Create case duplicate']");
    private final SelenideElement caseProcessingStatus = $x("//div[@title]/h6");
    private final SelenideElement userAvatarLocator = $x("//div[contains(@class,'MuiButtonBase-root')]//span[contains(@class,'MuiChip-label')]");
    private final SelenideElement documentElementContainer = $x("//li[contains(@class,'MuiListItem-container')]");
    private final SelenideElement documentSearchInput = $x("//div[@data-control-input='search']//input");
    private final SelenideElement noPagesInToWorkspaceLoc = $x("//p[text()='No page(s) in workspace']");
    private final SelenideElement stapleBtn = $x("//button[@data-action-fab='createStaple']");
    private final SelenideElement saveStapleBtn = $x("//button[@data-action-fab='saveStaple']");
    private final SelenideElement deleteStapleButton = $x(ACTION_BUTTON.formatted("deleteStaple"));
    private final SelenideElement editStapleButton = $x(ACTION_BUTTON.formatted("editStaple"));
    private final SelenideElement pagesInStapleTooltipLoc = $x("//div[@data-popper-placement='bottom']");
    private final SelenideElement pageCardPreviewLoc = $x("//div[contains(@class,'card-page-preview')]");
    private final SelenideElement addPageToWorkspaceBtn = $x(ACTION_BUTTON.formatted("addPageToWorkspace"));
    private final SelenideElement removePageFromWorkspaceBtn = $x(ACTION_BUTTON.formatted("removePageFromWorkspace"));
    private final SelenideElement totalNumberOfPagesInWorkspace = $x("//div[contains(@class,'MuiToolbar-regular') and .//*[contains(text(),'Total number of pages: ')]]");
    private final SelenideElement scrollUpBtn = $x("//button[contains(@class,'MuiFab-root') and .//@data-testid='KeyboardArrowUpIcon']");

    private final ElementsCollection caseDocumentsTitles = $$x("//ul[contains(@class,'MuiList-root')]//*[contains(@data-scroll-id,'document')]");
    private final ElementsCollection documentsList = $$x("//ul[contains(@class,'MuiList-root')]/li");
    private final ElementsCollection stapledIcon = $$x("//i[contains(@class, 'icon-stapled')]");
    private final ElementsCollection pageCardsLoc = $$x("//div[contains(@class,'scrollListContainer')]//div[contains(@class,'card-page-wrapper')]");
    private final ElementsCollection pageCardImageLocator = $$x("//img[@class='card-page-image-body']");

    private static void addPagesToStaple(int startIndex, int endIndex) {
        IntStream.range(startIndex, endIndex).boxed().forEach(index -> {
            $x(PAGE_LOC_BY_NUMBER.formatted(index) + ADD_PARTICULAR_PAGE_TO_STAPLE)
                    .scrollIntoView(SCROLL_TO_PARAMETER)
                    .shouldBe(visible)
                    .click();
            $x(PAGE_LOC_BY_NUMBER.formatted(index) + PARTICULAR_PAGE_IN_STAPLE).should(appear).shouldBe(visible);
        });
    }

    public SelenideElement findCaseTitleElementOnSearch(String searchTerm) {
        return $x("//a[contains(@href, 'documents') and text()='%s']".formatted(searchTerm));
    }

    public SelenideElement findCaseTitleElement(String searchTerm) {
        return $x("//div[contains(@class,'MuiToolbar-root') and .//@title='%s']".formatted(searchTerm));
    }

    @Step("Open home page")
    public HomePage openHomePage() {
        open(BASE_URL);
        userAvatarLocator.shouldBe(visible);
        return this;
    }

    @Step("Open {0} case")
    public HomePage openCase(String title) {
        log.info("Open case: {}", title);
        searchInput.shouldBe(visible).sendKeys(title, Keys.ENTER);
        findCaseTitleElementOnSearch(title).shouldBe(enabled).click();
        findCaseTitleElement(title).should(appear).shouldBe(visible);
        return this;
    }

    public void expandCaseContextMenu() {
        caseContextMenuButton.shouldBe(enabled).click();
        caseContextMenuList.should(exist).shouldBe(visible);
    }

    public HomePage openDuplicateAction() {
        expandCaseContextMenu();
        caseContextMenuList.$x(".//li[contains(@name,'Duplicate case')]").scrollTo().click();
        caseDuplicateDialog.should(exist).shouldBe(visible);
        return this;
    }

    public List<Document> getParsedDocuments() {
        log.info("Document parsing");
        return caseDocumentsTitles.shouldHave(CollectionCondition.sizeGreaterThan(0))
                .asFixedIterable().stream().map(element -> {
                    var title = element.$x(".//span[contains(@class,'MuiTypography-subtitle')]").text();
                    var numPages = element.$x(".//p[contains(@class,'MuiTypography-body')]/span")
                            .getText()
                            .replace("page(s)", "")
                            .trim();
                    var processingStatus = element.getAttribute("data-document-status");
                    return Document.builder().title(title).numPages(Integer.parseInt(numPages)).status(processingStatus).build();
                }).toList();
    }

    @Step("Wait util document processed")
    public HomePage waitUntilDocumentsProcessed(List<Document> documents) {
        log.info("Case documents processing");
        var isDocumentStatusReady = false;
        var isCaseTitleReady = false;

        for (int attempt = 0; attempt < 30; attempt++) {
            isDocumentStatusReady = documents.stream().allMatch(status -> status.getStatus().equals(READY.getDisplayName()));
            isCaseTitleReady = caseProcessingStatus.getText().equalsIgnoreCase(READY.getDisplayName());

            if (isDocumentStatusReady && isCaseTitleReady) return this;
            sleep(Duration.ofSeconds(20).toMillis());
            refresh();
        }
        if (isDocumentStatusReady || isCaseTitleReady) {
            throw new IllegalArgumentException("Not all documents were processed in allocated 10 minutes");
        }
        return this;
    }

    @Step("Open document")
    public HomePage openDocument(@NonNull String title) {
        searchDocument(title);
        documentElementContainer.$x(".//a[contains(@data-document-status,'ready') and .//text()='%s']".formatted(title)).click();
        closePreviewModalIfShown();
        pageCardsLoc.shouldHave(CollectionCondition.sizeGreaterThan(0));
        waitUntilPageCardIsLoading();
        return this;
    }

    private void searchDocument(String title) {
        log.info("Searching {} document", title);
        clearInput(documentSearchInput);
        documentSearchInput.shouldBe(enabled).sendKeys(title);
        documentsList.shouldHave(CollectionCondition.sizeGreaterThan(0));
    }

    public WorkspacePage openWorkspace() {
        openCaseTab("workspace");
        return new WorkspacePage();
    }

    @Step("Open {0} case tab")
    public void openCaseTab(String tabName) {
        log.info("Opening {} tab", tabName);
        closePreviewModalIfShown();
        $x("//a[@data-action-button-tab='%s']".formatted(tabName)).shouldBe(enabled).shouldBe(visible).click();
        $x(SELECTED_CASE_TAB_LOC.formatted(tabName)).should(appear).shouldBe(visible);
    }

    @Step("Reset workspace")
    public void resetWorkspace() {
        log.info("Removing pages from workspace");
        openWorkspace();
        while (!noPagesInToWorkspaceLoc.isDisplayed()) {
            pageCardsLoc.shouldHave(CollectionCondition.sizeGreaterThan(0));
            removePageFromWorkspaceBtn.shouldBe(visible).shouldBe(enabled).click();
            waitTillBubbleMessageShown("Page was removed from workspace");
            closeAllBubbles();
        }
    }

    private void scrollToParticularPage(int mainPage) {
        log.info("Screen is moving to page: {}", mainPage);
        goToPage(mainPage);
        do {
                actions().scrollToElement(pageCardsLoc.get(mainPage)).scrollByAmount(0, 333);
                waitUntilPageCardIsLoading();
            } while (!$x(PAGE_LOC_BY_NUMBER.formatted(mainPage)).isDisplayed());
            closePreviewModalIfShown();
    }

    private void goToPage(int mainPage) {
        clearInput(goToPageInput);
        goToPageInput.shouldBe(exist).sendKeys(String.valueOf(mainPage));
        goToPageBtn.shouldBe(enabled).click();
    }

    private void waitUntilPageCardIsLoading() {
        closePreviewModalIfShown();
        pageCardImageLocator.shouldHave(CollectionCondition.sizeGreaterThan(1))
                .last()
                .should(appear)
                .shouldBe(visible);
    }

    @Step("Staple pages from {0} to {1}")
    public HomePage staplePages(int startIndex, int endIndex) {
        log.info("Stapling pages: {}", startIndex);
        scrollToParticularPage(startIndex);
        closePreviewModalIfShown();
        stapleBtn.shouldBe(enabled).click();
        addPagesToStaple(startIndex, endIndex);
        saveStaple();
        return this;
    }

    @Step("Save staple")
    public void saveStaple() {
        log.info("Saving staple");
        saveStapleBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown("Staple was created", "Staple was updated");
        closeAllBubbles();
    }

    @Step("Perform staple edit")
    public HomePage performStapleEdit(int mainStaplePage) {
        log.info("Moving staple on edit mode");
        pageCardsLoc.get(mainStaplePage).shouldBe(visible).scrollIntoView(SCROLL_TO_PARAMETER);
        editStapleButton.shouldBe(enabled).click();
        return this;
    }

    @Step("Add pages from {0} to {1} on edit")
    public HomePage addPagesToStapleOnEdit(int startIndex, int pagesSize) {
        log.info("Pages from {} to {} are added to staple on edit mode", startIndex, pagesSize);
        scrollToParticularPage(startIndex);
        addPagesToStaple(startIndex, pagesSize);
        return this;
    }

    @Step("Remove pages from {0} to {1} on edit")
    public HomePage removePagesFromStapleOnEdit(int startIndex, int pagesSize) {
        log.info("Removing pages from {} to {}", startIndex, pagesSize);
        scrollToParticularPage(startIndex);
        IntStream.range(startIndex, startIndex + pagesSize).boxed().forEach(integer -> {
            $x(PAGE_LOC_BY_NUMBER.formatted(integer) + PARTICULAR_PAGE_IN_STAPLE)
                    .scrollIntoView(SCROLL_TO_PARAMETER)
                    .shouldBe(visible)
                    .click();
            $x(PAGE_LOC_BY_NUMBER.formatted(integer) + ADD_PARTICULAR_PAGE_TO_STAPLE).should(appear).shouldBe(visible);
        });
        return this;
    }

    public Integer getNumOfPagesInStaple(int mainPage) {
        scrollToParticularPage(mainPage);
        stapledIcon.shouldHave(CollectionCondition.sizeGreaterThan(0));
        $x(FIRST_PAGE_IN_STAPLE_BY_PAGE_NUM.formatted(mainPage) + "//i[contains(@class, 'icon-stapled')]").doubleClick().hover();
        return Integer.valueOf(pagesInStapleTooltipLoc.getText().replace("page(s) in staple", "").trim());
    }

    @Step("Delete staple with main page #{0}")
    public HomePage deleteStaple(int mainPage) {
        log.info("Deleting staple");
        scrollToParticularPage(mainPage);
        deleteStapleButton.shouldBe(exist).click();
        confirmAction("Delete staple from pages");
        waitTillBubbleMessagesShown("Staple was removed", "Staple was updated");
        closeAllBubbles();
        return this;
    }

    private void closePreviewModalIfShown() {
        while (pageCardPreviewLoc.isDisplayed()) {
            pageCardPreviewLoc.$x(CLOSE_PAGE_CARD_PREVIEW_BUTTON).should(appear).shouldBe(visible).click();
        }
    }

    @Step("Open #{0} page")
    public ModalPage openPage(int page) {
        scrollToParticularPage(page);
        $x(PAGE_LOC_BY_NUMBER.formatted(page)).should(appear).shouldBe(visible).click();
        return new ModalPage().verifyIfPageOpened();
    }

    public void refreshPage() {
        refresh();
    }

    public HomePage addPageToWorkspace(int numbers) {
        IntStream.range(1, numbers).boxed().forEach(page -> {
            scrollToParticularPage(page);
            $x(PAGE_LOC_BY_NUMBER.formatted(page) + ACTION_BUTTON.formatted("addPageToWorkspace")).shouldBe(visible).click();
            waitTillBubbleMessageShown("Page was added to workspace");
            closeAllBubbles();
        });
        return this;
    }

    public int getNumOfPagesInWorkspace(){
        return Integer.parseInt(totalNumberOfPagesInWorkspace.getText());
    }
}
