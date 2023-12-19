package regression;

import dto.Episode;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.AuthorRegistryPage;
import page.object.portal.cases.LoginPage;
import smoke.BaseAbstractTest;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static constants.DocumentConstants.SMITH_DEMO_FISHING_PDF;
import static constants.EpisodeConstants.SMITH_DEMO_EASTER_EPISODE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static utils.Config.*;

@Slf4j
public class AuthorRegistryTest extends BaseAbstractTest {
    private final AuthorRegistryPage authorRegistryTest = new AuthorRegistryPage();
    private static final String AUTHOR_NAME = SMITH_DEMO_EASTER_EPISODE.getAuthor();
    private static final String DOCUMENT_TITLE = SMITH_DEMO_FISHING_PDF.getTitle();
    private static final int DOCUMENT_PAGE = SMITH_DEMO_FISHING_PDF.getNumPages();

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @AfterMethod(onlyForGroups = "authorRegistry")
    public void cleanUp() {
        deleteAllEpisodes(DOCUMENT_TITLE, DOCUMENT_PAGE);
        deleteAuthorFromRegistry(AUTHOR_NAME);
    }

    @Test(priority = 1, groups = "authorRegistry")
    public void verifyIfAuthorRegistryPageIsEnabled() {
        homePage.openHomePage().openAuthorRegistry().markAuthorVerified(AUTHOR_NAME);

        assertThat(getWebDriver().getCurrentUrl())
                .as("Wrong page url is displayed")
                .isEqualTo(BASE_URL.concat("/admin/author-registry"));
    }

    @Test(priority = 2, groups = "authorRegistry")
    public void verifyIfNewlyCreatedAuthorIsAppearedInAuthorRegistry() {
        var foundAuthorName = homePage
                .openHomePage()
                .openAuthorRegistry()
                .searchAuthor(AUTHOR_NAME);

        assertThat(foundAuthorName).as("Author shouldn't be exist at the beginning of the test").isNull();
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);
        homePage
                .openHomePage()
                .openAuthorRegistry()
                .searchAuthor(AUTHOR_NAME);

        assertThat(authorRegistryTest.getAuthorName())
                .as("Author shouldn't be exist at the beginning of the test")
                .isEqualTo(AUTHOR_NAME);
    }

    @Test(priority = 3, groups = "authorRegistry")
    public void verifyAuthorAutoSuggestion() {
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);
        var authors = modalPage
                .openCreateEpisodeForm()
                .tryToFindAuthorByKeyWords(AUTHOR_NAME.substring(0, 5))
                .getListOfSuggestedAuthors();
        assertThat(authors).as("Previously added Author should be pre-selected on click").contains(AUTHOR_NAME);
    }

    @Test(priority = 4, groups = "authorRegistry")
    public void verifyIfAuthorMarksAsVerified() {
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);
        homePage
                .openHomePage()
                .openAuthorRegistry()
                .searchAuthor(AUTHOR_NAME)
                .markAuthorVerified(AUTHOR_NAME);

        assertThat(authorRegistryTest.verifyIfVerifiedIconIsAppeared(AUTHOR_NAME))
                .as("'Verified' icon is not appeared")
                .isTrue();
        tryToFindAuthorByKeyWords(DOCUMENT_TITLE, DOCUMENT_PAGE);

        assertThat(modalPage.verifyIsSuggestedAuthorMarkedAsVerified(AUTHOR_NAME))
                .as("'Verified' icon is not placed in author field near the name")
                .isTrue();
    }

    @Test(priority = 5, groups = "authorRegistry")
    @Description("User enters an existing author name and just saves it")
    public void verifyIfAlreadyExistedAuthorIsNotOverCreated() {
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);

        assertThat(getListSizeOfFoundAuthors())
                .as("Author is over rewritten")
                .isEqualTo(1);
        assertThat(authorRegistryTest.getAuthorName())
                .as("Displays wrong author name")
                .isEqualTo(AUTHOR_NAME);
    }

    @Test(priority = 6, groups = "authorRegistry")
    @Description("User enters an existing name, but does not click on the suggested name, but saves it as is")
    public void verifyIfAlreadyExistedAuthorIsNotOverCreated_2() {
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);
        modalPage
                .openCreateEpisodeForm()
                .tryToFindAuthorByKeyWords(AUTHOR_NAME)
                .saveEpisode();
        var parsedEpisode = modalPage.parseEpisode();

        assertThat(parsedEpisode)
                .as("Episode data is not matched")
                .isEqualTo(Episode.builder().type(null).date(null).time(null).author(AUTHOR_NAME).build());

        assertThat(getListSizeOfFoundAuthors())
                .as("Author is over rewritten")
                .isEqualTo(1);
    }

    @Test(priority = 7, groups = "authorRegistry")
    @Description("User enters an existing author name, but adds an extra space before and after the author name")
    public void verifyIfAlreadyExistedAuthorIsNotOverCreated_3() {
        createEpisode(DOCUMENT_TITLE, DOCUMENT_PAGE);
        modalPage
                .openCreateEpisodeForm()
                .tryToFindAuthorByKeyWords("  " + AUTHOR_NAME + "  ")
                .saveEpisode();

        var parsedEpisode = modalPage.parseEpisode();
        assertThat(parsedEpisode)
                .as("Episode data is not matched")
                .isEqualTo(Episode.builder().type(null).date(null).time(null).author(AUTHOR_NAME).build());

        assertThat(getListSizeOfFoundAuthors()).as("Author is over rewritten").isEqualTo(1);
    }

    @Step("Find author by name and get list of found authors")
    private int getListSizeOfFoundAuthors() {
        return homePage
                .openHomePage()
                .openAuthorRegistry()
                .searchAuthor(AUTHOR_NAME)
                .getSizeOfListOfFoundAuthor();
    }

    @Step("Try to find Author by typing part of his name")
    private void tryToFindAuthorByKeyWords(String documentTitle, int numPages) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentTitle)
                .openPage(numPages)
                .openCreateEpisodeForm()
                .tryToFindAuthorByKeyWords(AUTHOR_NAME.substring(0, 5));
    }

    @Step("Create page episode on {1} page in {0} document")
    private void createEpisode(String documentTitle, int number) {
        log.info("Create episode on {} page number and for {} document", number, documentTitle);
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentTitle)
                .openPage(number)
                .openCreateEpisodeForm()
                .fillInEpisodeForm(SMITH_DEMO_EASTER_EPISODE)
                .saveEpisode();
    }

    @Step("Delete all episodes")
    public void deleteAllEpisodes(String documentName, int pageNum) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentName)
                .openPage(pageNum)
                .deleteAllEpisodes();
    }

    @Step("Delete Author from author registry")
    public void deleteAuthorFromRegistry(String authorName) {
        homePage
                .openHomePage()
                .openAuthorRegistry()
                .deleteAuthor(authorName);
    }
}
