package page.object.portal.cases;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.Document;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static enums.ProcessingStatus.READY;
import static utils.Config.BASE_URL;
import static utils.JsUtil.waitForDomToLoad;

@Slf4j
public class HomePage extends BaseAbstractPage{
    private static final String ACTION_BUTTON = "//button[@data-action-button='%s']";
    private static final String ACTION_TAB_BUTTON = "//button[@data-action-button-tab='%s']";
    private static final String SCROLL_TO_PARAMETER = "{block: \"center\", inline: \"start\"}";
    private static final String SELECTED_CASE_TAB_LOC = "//a[contains(@href,'%s') and @aria-selected='true']";
    private static final String PAGE_LOC_BY_NUMBER = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d']";
    private static final String ADD_PARTICULAR_PAGE_TO_STAPLE = "//button[.//i[contains(@class,'icon-add')]]";
    private static final String PARTICULAR_PAGE_IN_STAPLE = "//button[.//i[contains(@class,'icon-done')]]";
    private static final String CLOSE_PAGE_CARD_PREVIEW_BUTTON = ".//button[.//*[@data-testid='CloseIcon']]";
    private static final String GO_TO_INPUT_LOC = "//form[.//div[@aria-label='Go to input']]";
    private static final String DROP_DOWN_LIST_VALUE_LOC = "//ul/li[contains(@name,'%s')]";
    private static final String FIRST_PAGE_IN_STAPLE_BY_PAGE_NUM = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d' and @data-page-staple-order='1']";
    private static final String CASE_CONTAINER_LOC = "//div[contains(@class,'MuiPaper-root') and .//a[contains(text(),'%s')]]";
    private static final String ACTIVE_CASES_TAB_LOC = "//div[contains(@class,'MuiToolbar-dense') and .//button[contains(@data-action-button-tab,'%s') and @aria-selected='true']]";
    private static final String CASE_CONTEXT_MENU_BTN = "//button[contains(@data-action-button,'caseDropdown')]";
    private static final String HOME_PAGE_CONTEXT_MENU_LIST = "//ul[contains(@role,'menu')]//*[@data-action-menu-item='%s']";

    private final SelenideElement deleteCaseDialogLoc = $x("//div[contains(@role, 'dialog') and .//*[normalize-space()='Are you absolutely sure ?']]");
    private final SelenideElement submitCaseDeleteBtn = $x("//button[@data-action-button='submitDeleteCaseDialog']");
    private final SelenideElement goToPageInput = $x(GO_TO_INPUT_LOC + "//input");
    private final SelenideElement goToPageBtn = $x(GO_TO_INPUT_LOC + "//button[contains(text(),'Go to page')]");
    private final SelenideElement searchInput = $x("//div[@data-control-input='search']//input");
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
    private final SelenideElement removePageFromWorkspaceBtn = $x(ACTION_BUTTON.formatted("removePageFromWorkspace"));
    private final SelenideElement homeContextMenuBtn = $x("//div[contains(@class,'MuiChip-root') and @role='button']");

    private final ElementsCollection authorRegistryItems = $$x("//div[contains(@class,'MuiContainer-root')]//ul/li");
    private final ElementsCollection pageCardImageLocator = $$x("//img[@class='card-page-image-body']");
    private final ElementsCollection caseDocumentsTitles = $$x("//ul[contains(@class,'MuiList-root')]//*[contains(@data-scroll-id,'document')]");
    private final ElementsCollection documentsList = $$x("//ul[contains(@class,'MuiList-root')]/li");
    private final ElementsCollection stapledIcon = $$x("//i[contains(@class, 'icon-stapled')]");
    private final ElementsCollection pageCardsLoc = $$x("//div[contains(@class,'scrollListContainer')]//div[contains(@class,'card-page-wrapper')]");

    private static void addPagesToStaple(int startIndex, int endIndex) {
        log.info("Adding to the staple pages from {} to {}", startIndex, endIndex);
        IntStream.range(startIndex, endIndex).boxed().forEach(index -> {
            $x(PAGE_LOC_BY_NUMBER.formatted(index) + ADD_PARTICULAR_PAGE_TO_STAPLE).scrollIntoView(SCROLL_TO_PARAMETER).shouldBe(visible).click();
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
        log.info("Open cases home page");
        open(BASE_URL);
        verifyPageIsLoaded();
        return this;
    }

    public HomePage verifyPageIsLoaded() {
        userAvatarLocator.shouldBe(visible);
        $x(ACTIVE_CASES_TAB_LOC.formatted("open")).shouldBe(visible);
        return this;
    }

    @Step("Open {0} case")
    public HomePage openCase(String title) {
        log.info("Open case: {}", title);
        searchCaseByTitle(title);
        findCaseTitleElementOnSearch(title).shouldBe(enabled).click();
        findCaseTitleElement(title).should(appear).shouldBe(visible);
        return this;
    }

    public void expandCaseContextMenu() {
        $x(CASE_CONTEXT_MENU_BTN).shouldBe(enabled).click();
        caseContextMenuList.should(exist).shouldBe(visible);
    }

    public void openDuplicateAction() {
        expandCaseContextMenu();
        caseContextMenuList.$x(".//li[contains(@name,'Duplicate case')]").scrollTo().click();
        caseDuplicateDialog.should(exist).shouldBe(visible);
    }

    public List<Document> getParsedDocuments() {
        log.info("Parsing of document");
        return caseDocumentsTitles.shouldHave(CollectionCondition.sizeGreaterThan(0)).asFixedIterable().stream().map(element -> {
            var title = element.$x(".//span[contains(@class,'MuiTypography-subtitle')]").text();
            var numPages = element.$x(".//p[contains(@class,'MuiTypography-body')]/span").getText().replace("page(s)", "").trim();
            var processingStatus = element.getAttribute("data-document-status");
            return Document.builder().title(title).numPages(Integer.parseInt(numPages)).status(processingStatus).build();
        }).toList();
    }

    @Step("Wait util document processed")
    public HomePage waitUntilDocumentsProcessed(List<Document> documents) {
        log.info("Processing of Case documents");
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

    @Step("Open {0} document")
    public HomePage openDocument(@NonNull String title) {
        log.info("Open {0} document");
        searchDocument(title);
        documentElementContainer.$x(".//a[contains(@data-document-status,'ready') and .//text()='%s']".formatted(title)).click();
        pageCardImageLocator.shouldHave(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    @Step("Search document")
    private void searchDocument(String title) {
        log.info("Search {} document", title);
        clearInput(documentSearchInput);
        documentSearchInput.shouldBe(enabled).sendKeys(title);
        documentsList.shouldHave(CollectionCondition.sizeGreaterThan(0));
    }

    public WorkspacePage openWorkspace() {
        openCaseTab("workspace");
        return new WorkspacePage();
    }

    public HomePage openArchivedTab() {
        $x(ACTION_TAB_BUTTON.formatted("archived")).click();
        $x(ACTIVE_CASES_TAB_LOC.formatted("archived")).should(appear).shouldBe(visible);
        return this;
    }

    @Step("Open {0} case tab")
    public void openCaseTab(String tabName) {
        log.info("Open {} tab", tabName);
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
        log.info("Screen is moving to the page: {}", mainPage);
        goToPage(mainPage);
        while (!$x(PAGE_LOC_BY_NUMBER.formatted(mainPage)).isDisplayed()) {
            actions().scrollToElement(pageCardsLoc.get(mainPage)).scrollByAmount(0, 333);
            waitUntilPageCardIsLoading();
        }
    }

    private void goToPage(int mainPage) {
        clearInput(goToPageInput);
        goToPageInput.shouldBe(exist).sendKeys(String.valueOf(mainPage));
        goToPageBtn.shouldBe(enabled).click();
    }

    @Step("Staple pages from {0} to {1}")
    public HomePage staplePages(int startIndex, int endIndex) {
        log.info("Stapling pages from {} to {}", startIndex, endIndex);
        scrollToParticularPage(startIndex);
        closePreviewModalIfShown();
        stapleBtn.shouldBe(enabled).click();
        addPagesToStaple(startIndex, endIndex);
        saveStaple();
        return this;
    }

    @Step("Save staple")
    public void saveStaple() {
        log.info("Save staple");
        saveStapleBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown("Staple was created", "Staple was updated");
        closeAllBubbles();
    }

    @Step("Perform staple edit")
    public HomePage performStapleEdit(int mainStaplePage) {
        log.info("Switch staple on edit mode");
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
            $x(PAGE_LOC_BY_NUMBER.formatted(integer) + PARTICULAR_PAGE_IN_STAPLE).scrollIntoView(SCROLL_TO_PARAMETER).shouldBe(visible).click();
            $x(PAGE_LOC_BY_NUMBER.formatted(integer) + ADD_PARTICULAR_PAGE_TO_STAPLE).should(appear).shouldBe(visible);
        });
        return this;
    }

    public Integer getNumOfPagesInStaple(int mainPage) {
        log.info("Get the page number that are in staple");
        scrollToParticularPage(mainPage);
        stapledIcon.shouldHave(CollectionCondition.sizeGreaterThan(0));
        $x(FIRST_PAGE_IN_STAPLE_BY_PAGE_NUM.formatted(mainPage) + "//i[contains(@class, 'icon-stapled')]").doubleClick().hover();
        return Integer.valueOf(pagesInStapleTooltipLoc.getText().replace("page(s) in staple", "").trim());
    }

    @Step("Delete staple with main page #{0}")
    public HomePage deleteStaple(int mainPage) {
        log.info("Delete staple");
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
        log.info("Open page number {}", page);
        scrollToParticularPage(page);
        $x(PAGE_LOC_BY_NUMBER.formatted(page)).should(appear).shouldBe(visible).click();
        return new ModalPage().verifyIfPageOpened();
    }

    @Step("Add #{0} page into Workspace")
    public void addPageToWorkspace(int pageNum) {
        log.info("Add page {} into Workspace", pageNum);
        scrollToParticularPage(pageNum);
        $x(PAGE_LOC_BY_NUMBER.formatted(pageNum) + ACTION_BUTTON.formatted("addPageToWorkspace")).shouldBe(visible).click();
        waitTillBubbleMessageShown("Page was added to workspace");
        closeAllBubbles();
    }

    @Step("Delete case {0}")
    public void deleteCase(String title) {
        if (isCaseShown(title)) {
            $x(CASE_CONTAINER_LOC.formatted(title) + CASE_CONTEXT_MENU_BTN).shouldBe(enabled).click();
            $x(DROP_DOWN_LIST_VALUE_LOC.formatted("Delete case")).shouldBe(visible).click();
            approveDeleteCase(title);
        }
    }

    private void approveDeleteCase(String title) {
        deleteCaseDialogLoc.shouldBe(appear);
        $x("//input[@name='caseName']").sendKeys(title);
        submitCaseDeleteBtn.shouldBe(enabled).click();
        waitTillBubbleMessageShown("Case was deleted");
        closeAllBubbles();
        deleteCaseDialogLoc.should(disappear);
    }

    @Step("Archive case {0}")
    public HomePage archiveCase(String title) {
        openHomePage();
        searchCaseByTitle(title);
        confirmCaseArchive(title);
        return this;
    }

    private static void confirmCaseArchive(String title) {
        $x(CASE_CONTAINER_LOC.formatted(title) + CASE_CONTEXT_MENU_BTN).shouldBe(enabled).click();
        $x(DROP_DOWN_LIST_VALUE_LOC.formatted("Archive case")).shouldBe(visible).click();
        waitTillBubbleMessageShown("Case was archived");
        closeAllBubbles();
    }

    public void searchCaseByTitle(String title) {
        clearInput(searchInput);
        searchInput.shouldBe(visible).sendKeys(title, Keys.ENTER);
    }

    public boolean isCaseShown(String caseName) {
        waitForDomToLoad();
        return $x(CASE_CONTAINER_LOC.formatted(caseName)).is(visible);
    }

    private void waitUntilPageCardIsLoading() {
        closePreviewModalIfShown();
        pageCardImageLocator.shouldHave(CollectionCondition.sizeGreaterThan(0))
                .first()
                .should(appear)
                .shouldBe(visible);
    }

    public AuthorRegistryPage openAuthorRegistry() {
        homeContextMenuBtn.shouldBe(visible).click();
        $x(HOME_PAGE_CONTEXT_MENU_LIST.formatted("authorRegistry")).shouldBe(visible).click();
        authorRegistryItems.shouldHave(CollectionCondition.sizeGreaterThan(0));
        $x("//div[h6[contains(text(),'Author Registry')]]").shouldBe(visible);
        return new AuthorRegistryPage();
    }
}
