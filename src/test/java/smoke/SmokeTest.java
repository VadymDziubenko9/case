package smoke;

import io.qameta.allure.Description;
import listeners.AttachmentListener;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import page.object.portal.cases.CaseDuplicationPage;
import page.object.portal.cases.HomePage;
import page.object.portal.cases.LoginPage;

import static enums.DuplicateOptions.getExpectedOptionsList;
import static enums.TagOps.BILLS;
import static enums.TagOps.LEGAL;
import static enums.Team.QA_TEAM;
import static org.assertj.core.api.Assertions.assertThat;
import static page.object.portal.cases.CaseDuplicationPage.getExpectedMediaList;
import static page.object.portal.cases.HomePage.HOME_URL;
import static page.object.portal.models.Document.getDocumentsListOfTitles;
import static page.object.portal.models.Document.getExpectedDocumentsList;
import static widgets.TagWidget.*;

@Listeners({AttachmentListener.class})
@Slf4j
public class SmokeTest extends BaseTest {
    private final static String COPIED_CASE_NAME = "Copy of John Smith";
    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final CaseDuplicationPage caseDuplicationPage = new CaseDuplicationPage();
    private static final int START_FROM_INDEX = 5;

    @Description("Workspace Cleanup")
    public void workspaceCleanupAndStapleDeletion() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .deleteStaple(START_FROM_INDEX)
                .resetWorkspace();
    }

    @Test(description = "Login user", groups = "smoke")
    @Description("Test Description: Login test")
    public void login() {
        loginPage.login("vadymdziubenko99+otto@gmail.com", "Ps@!2009");
        assertThat(loginPage.verifyLoggedInUserName())
                .as("Wrong username is displaying after logged in")
                .isEqualTo("Otto von Bismarck");
        assertThat(loginPage.verifyCasesPageUrl())
                .as("Wrong url is displaying after logged in")
                .isEqualTo(HOME_URL);
    }

    @Test(description = "Duplication of an existing case scenario")
    @Description("Test Description: Test for duplication of an existing case")
    public void verifyDuplicateCase() {
        var parsedDocuments = homePage.openHomePage().openCase("John Smith").getParsedDocuments();

        homePage.openDuplicateAction();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(parsedDocuments)
                    .as("Uploaded and suggested in duplicate list documents aren't matched")
                    .isEqualTo(getExpectedDocumentsList());

            softAssertions.assertThat(caseDuplicationPage.getDropDownMedia())
                    .as("Uploaded and suggested in duplicate media aren't matched")
                    .isEqualTo(getExpectedMediaList());

            softAssertions.assertThat(caseDuplicationPage.getDropDownOptions())
                    .as("Uploaded and suggested in duplicate list options aren't matched")
                    .isEqualTo(getExpectedOptionsList());
        });

        caseDuplicationPage
                .setDocuments(getDocumentsListOfTitles())
                .setMedia(getExpectedMediaList())
                .setOptions(getExpectedOptionsList())
                .setTeam(QA_TEAM.getName())
                .submitCaseDuplicate();

        var parsedCopiedDocuments = homePage.openHomePage()
                .openCase(COPIED_CASE_NAME)
                .waitUntilDocumentsProcessed(parsedDocuments)
                .getParsedDocuments();

        assertThat(parsedDocuments)
                .as("Documents in the main case and in the copied one aren't matched")
                .containsExactlyInAnyOrderElementsOf(parsedCopiedDocuments);
    }

    @Test(description = "Stapling single-incident pages", groups = "smoke", dependsOnMethods = "login")
    @Description("Test Description: Stapling single-incident pages test with appending and detaching")
    public void verifyPagesStapling() {
        try {
            var pagesCount = 15;
            int numOfStapledPages = START_FROM_INDEX + pagesCount;
                homePage
                        .openHomePage()
                        .openCase(COPIED_CASE_NAME)
                        .openDocument(getDocumentsListOfTitles())
                        .staplePages(START_FROM_INDEX, numOfStapledPages);

                assertThat(homePage.getNumOfPagesInStaple(START_FROM_INDEX))
                        .as("Appropriate amount of stapled pages should be displayed")
                        .isEqualTo(numOfStapledPages);

                homePage
                        .performStapleEdit(START_FROM_INDEX)
                        .removePagesFromStapleOnEdit(numOfStapledPages, START_FROM_INDEX)
                        .saveStaple();

                assertThat(homePage.getNumOfPagesInStaple(START_FROM_INDEX))
                        .as("Appropriate amount of stapled pages should be displayed after remove %d pages".formatted(START_FROM_INDEX))
                        .isEqualTo(numOfStapledPages - START_FROM_INDEX);

                homePage
                        .performStapleEdit(START_FROM_INDEX)
                        .addPagesToStapleOnEdit(1, START_FROM_INDEX - 1)
                        .saveStaple();

                assertThat(homePage.getNumOfPagesInStaple(1))
                        .as("Appropriate amount of stapled pages should be displayed after add %d pages".formatted(START_FROM_INDEX - 1))
                        .isEqualTo(numOfStapledPages - 1);

        } finally {
            workspaceCleanupAndStapleDeletion();
        }
    }

    @Test(description = "User should be able to add tags to the page", groups = "smoke", dependsOnMethods = "login")
    @Description("Test Description: Tagging pages")
    public void verifyPagesTagging() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles());
        setPageTag(1, LEGAL.getName().toLowerCase());
        homePage.refreshPage();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(getDocumentPageTag(1))
                    .as("Tag should be correctly shown on page card in the file view")
                    .isEqualTo(LEGAL.getName());

            var modalPage = homePage.openPage(1);
            softAssertions.assertThat(getPageTag())
                    .as("Tag should be correctly shown in the modal view")
                    .isEqualTo(LEGAL.getName());

            setPageTagOnModalView(BILLS.getName().toLowerCase());
            modalPage.refreshPage();
            softAssertions.assertThat(getDocumentPageTag(1))
                    .as("Tag should be correctly shown on page card in the file view after edit")
                    .isEqualTo(BILLS.getName());

            homePage.openPage(1);
            softAssertions.assertThat(getPageTag())
                    .as("Tag should be correctly shown in the modal view")
                    .isEqualTo(BILLS.getName());

            untagPage(1);
            modalPage.closeModalView();
            softAssertions.assertThat(getDocumentPageTag(1))
                    .as("Tag shouldn't be shown on page card in the file view after delete")
                    .isEqualTo("Set tag");

            homePage.openPage(1);
            softAssertions.assertThat(getPageTag())
                    .as("Tag shouldn't be shown in the modal view after delete")
                    .isNull();
        });
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    public void verifyEpisodeCreation() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .openPage(5)
                .openCreateEpisodeForm()
                .fillInEpisodeForm()
                .submit();

    }

    @Test(description = "User should be able to assign page to the Workspace view")
    public void verifyAssigningPageToWorkspace() {
    }

    @Test(description = "User should be able to add notes to existing episodes")
    public void verifyPageNotation() {
    }

}
