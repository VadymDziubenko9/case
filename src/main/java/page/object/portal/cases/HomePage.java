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
public class HomePage extends BaseAbstractPage {
    private static final String ACTION_BUTTON = "//button[@data-action-button='%s']";
    private static final String SELECTED_CASE_TAB_LOC = "//a[contains(@href,'%s') and @aria-selected='true']";
    private static final String PAGE_LOC_BY_NUMBER = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d']";
    private static final String ADD_PARTICULAR_PAGE_TO_STAPLE = "//button[.//i[contains(@class,'icon-add')]]";
    private static final String PARTICULAR_PAGE_IN_STAPLE = "//button[.//i[contains(@class,'icon-done')]]";
    private static final String GO_TO_INPUT_LOC = "//form[.//div[@aria-label='Go to input']]";
    private static final String DROP_DOWN_LIST_VALUE_LOC = "//ul/li[contains(@name,'%s')]";
    private static final String FIRST_PAGE_IN_STAPLE_BY_PAGE_NUM = "//div[contains(@class,'card-page-wrapper')]/div[@data-document-page-number='%d' and @data-page-staple-order='1']";
    private static final String CASE_CONTAINER_LOC = "//div[contains(@class,'MuiPaper-root') and .//a[contains(text(),'%s')]]";
    private static final String CASE_TITLE_ON_HOME_PAGE_LOC = "//a[contains(@href, 'documents') and text()='%s']";
    private static final String TITLE_INTO_CASE_LOC = "//div[contains(@class,'MuiToolbar-root') and .//@title='%s']";
    private static final String ACTIVE_CASES_TAB_LOC = "//div[contains(@class,'MuiToolbar-dense') and .//button[contains(@data-action-button-tab,'%s') and @aria-selected='true']]";
    private static final String ACTION_TAB_BUTTON = "//button[@data-action-button-tab='%s']";
    private static final String CASE_CONTEXT_MENU_BTN = "//button[contains(@data-action-button,'caseDropdown')]";
    private static final String HOME_PAGE_CONTEXT_MENU_LIST = "//ul[contains(@role,'menu')]//*[@data-action-menu-item='%s']";

    private static final String STAPLE_IS_CREATED = "Staple is created";
    private static final String STAPLE_IS_UPDATED = "Staple is updated";
    private static final String PAGE_IS_ADDED_TO_WORKSPACE = "Page is added to workspace";
    private static final String CASE_IS_ARCHIVED = "Case is archived";
    private static final String CASE_IS_DELETED = "Case is deleted";

    private final SelenideElement archivedTabBtnLoc = $x(ACTION_TAB_BUTTON.formatted("archived"));
    private final SelenideElement openCaseTabLoc = $x(ACTIVE_CASES_TAB_LOC.formatted("open"));
    private final SelenideElement archivedCaseTabLoc = $x(ACTIVE_CASES_TAB_LOC.formatted("archived"));
    private final SelenideElement deleteCaseDialogLoc = $x("//div[contains(@role, 'dialog') and .//*[normalize-space()='Are you absolutely sure ?']]");
    private final SelenideElement submitCaseDeleteBtn = $x("//button[@data-action-button='submitDeleteCaseDialog']");
    private final SelenideElement goToPageInput = $x(GO_TO_INPUT_LOC + "//input");
    private final SelenideElement goToPageBtn = $x(GO_TO_INPUT_LOC + "//button[contains(text(),'Go to page')]");
    private final SelenideElement searchInput = $x("//div[@data-control-input='search']//input");
    private final SelenideElement caseContextMenuList = $x("//ul[contains(@class,'MuiList-root') and @role='menu']");
    private final SelenideElement caseDuplicateDialog = $x("//form[contains(@role,'dialog') and .//text()='Create case duplicate']");
    private final SelenideElement caseProcessingStatus = $x("//div[@title]/h6");
    private final SelenideElement documentElementContainer = $x("//li[contains(@class,'MuiListItem-container')]");
    private final SelenideElement documentSearchInput = $x("//div[@data-control-input='search']//input");
    private final SelenideElement stapleBtn = $x("//button[@data-action-fab='createStaple']");
    private final SelenideElement saveStapleBtn = $x("//button[@data-action-fab='saveStaple']");
    private final SelenideElement pagesInStapleTooltipLoc = $x("//div[@data-popper-placement='bottom']");
    private final SelenideElement homeContextMenuBtn = $x("//div[contains(@class,'MuiChip-root') and @role='button']");
    private final SelenideElement casePortalLogoLoc = $x("//header//div[contains(@class,'MuiToolbar-root') and .//@alt='Case Chronology - Case Portal']");
    private final SelenideElement caseBatchLoc = $x("//ul[contains(@class,'MuiList-root')]//li[contains(@class,'MuiListSubheader-root')]");

    private final ElementsCollection authorRegistryItems = $$x("//div[contains(@class,'MuiContainer-root')]//ul/li");
    private final ElementsCollection pageCardImageLocator = $$x("//img[@class='card-page-image']");
    private final ElementsCollection caseDocumentsTitles = $$x("//ul[contains(@class,'MuiList-root')]//*[contains(@data-scroll-id,'document')]");
    private final ElementsCollection documentsList = $$x("//ul[contains(@class,'MuiList-root')]/li");
    private final ElementsCollection stapledIcon = $$x("//i[contains(@class, 'icon-stapled')]");
    private final ElementsCollection pageCardsLoc = $$x("//div[contains(@class,'card-page-container')]//div[contains(@class,'card-page-wrapper')]");

    private static void addPagesToStaple(int startIndex, int endIndex) {
        log.info("Adding to the staple pages from {} to {}", startIndex, endIndex);
        IntStream.range(startIndex, endIndex).boxed().forEach(index -> {
            $x(PAGE_LOC_BY_NUMBER.formatted(index) + ADD_PARTICULAR_PAGE_TO_STAPLE)
                    .scrollIntoView("{block: \"center\", inline: \"start\"}").shouldBe(visible).click();
            $x(PAGE_LOC_BY_NUMBER.formatted(index) + PARTICULAR_PAGE_IN_STAPLE).shouldBe(visible);
        });
    }

    private static void confirmCaseArchive(String title) {
        $x(CASE_CONTAINER_LOC.formatted(title) + CASE_CONTEXT_MENU_BTN).shouldBe(enabled).click();
        $x(DROP_DOWN_LIST_VALUE_LOC.formatted("Archive case")).shouldBe(visible).click();
        waitTillBubbleMessageShown(CASE_IS_ARCHIVED);
        closeAllBubbles();
    }

    @Step("Open home page")
    public HomePage openHomePage() {
        log.info("Open cases home page");
        open(BASE_URL);
        verifyIsUserLoadedIn();
        return this;
    }

    public HomePage verifyIsUserLoadedIn() {
        openCaseTabLoc.shouldBe(visible);
        casePortalLogoLoc.shouldBe(visible);
        return this;
    }

    @Step("Open {0} case")
    public HomePage openCase(String title) {
        log.info("Open case: {}", title);
        searchCaseByTitle(title);
        $x(CASE_TITLE_ON_HOME_PAGE_LOC.formatted(title)).shouldBe(visible).click();
        $x(TITLE_INTO_CASE_LOC.formatted(title)).shouldBe(visible);
        caseBatchLoc.shouldBe(exist, visible);
        return this;
    }

    public void expandCaseContextMenu() {
        $x(CASE_CONTEXT_MENU_BTN).shouldBe(enabled).click();
        caseContextMenuList.shouldBe(visible, exist);
    }

    public void openDuplicateAction() {
        expandCaseContextMenu();
        caseContextMenuList.$x(".//li[contains(@name,'Duplicate case')]").scrollTo().click();
        caseDuplicateDialog.shouldBe(visible, exist);
    }

    public List<Document> getParsedDocuments() {
        log.info("Parsing of document");
        return caseDocumentsTitles.shouldHave(CollectionCondition.sizeGreaterThan(0)).asFixedIterable().stream().map(element -> {
            var title = element.$x(".//span[contains(@class,'MuiTypography-subtitle')]").text().trim();
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

    @Step("Open document")
    public HomePage openDocument(@NonNull String title) {
        log.info("Open {} document", title);
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
        archivedTabBtnLoc.shouldBe(enabled).click();
        archivedCaseTabLoc.shouldBe(visible);
        return this;
    }

    @Step("Open {0} case tab")
    public void openCaseTab(String tabName) {
        log.info("Open {} tab", tabName);
        $x("//a[@data-action-button-tab='%s']".formatted(tabName)).shouldBe(enabled, visible).click();
        $x(SELECTED_CASE_TAB_LOC.formatted(tabName)).shouldBe(appear, visible);
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
        stapleBtn.shouldBe(enabled).click();
        addPagesToStaple(startIndex, endIndex);
        saveStaple();
        return this;
    }

    @Step("Save staple")
    public void saveStaple() {
        log.info("Save staple");
        saveStapleBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown(STAPLE_IS_CREATED, STAPLE_IS_UPDATED);
        closeAllBubbles();
    }

    public Integer getNumOfPagesInStaple(int mainPage) {
        log.info("Get the page number that are in staple");
        scrollToParticularPage(mainPage);
        stapledIcon.shouldHave(CollectionCondition.sizeGreaterThan(0));
        $x(FIRST_PAGE_IN_STAPLE_BY_PAGE_NUM.formatted(mainPage) + "//i[contains(@class, 'icon-stapled')]").doubleClick().hover();
        return Integer.valueOf(pagesInStapleTooltipLoc.getText().replace("page(s) in staple", "").trim());
    }

    @Step("Open #{0} page")
    public ModalPage openPage(int page) {
        log.info("Open page number {}", page);
        scrollToParticularPage(page);
        $x(PAGE_LOC_BY_NUMBER.formatted(page)).shouldBe(visible).click();
        return new ModalPage().verifyIfPageOpened();
    }

    @Step("Add #{0} page into Workspace")
    public void addPageToWorkspace(int pageNum) {
        log.info("Add page {} into Workspace", pageNum);
        scrollToParticularPage(pageNum);
        $x(PAGE_LOC_BY_NUMBER.formatted(pageNum) + ACTION_BUTTON.formatted("addPageToWorkspace")).shouldBe(visible).click();
        waitTillBubbleMessageShown(PAGE_IS_ADDED_TO_WORKSPACE);
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
        waitTillBubbleMessageShown(CASE_IS_DELETED);
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

    public void searchCaseByTitle(String title) {
        clearInput(searchInput);
        searchInput.shouldBe(visible).sendKeys(title, Keys.ENTER);
    }

    public boolean isCaseShown(String caseName) {
        waitForDomToLoad();
        return $x(CASE_CONTAINER_LOC.formatted(caseName)).is(visible);
    }

    private void waitUntilPageCardIsLoading() {
        while (!pageCardImageLocator.shouldHave(CollectionCondition.sizeGreaterThan(0)).last().isDisplayed()) {
            sleep(200);
        }
        pageCardImageLocator.last().shouldBe(visible, exist);
    }

    public AuthorRegistryPage openAuthorRegistry() {
        homeContextMenuBtn.shouldBe(visible).click();
        $x(HOME_PAGE_CONTEXT_MENU_LIST.formatted("authorRegistry")).shouldBe(visible).click();
        authorRegistryItems.shouldHave(CollectionCondition.sizeGreaterThan(0));
        $x("//div[h6[contains(text(),'Author Registry')]]").shouldBe(visible);
        return new AuthorRegistryPage();
    }
}