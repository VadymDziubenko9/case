package smoke;

import io.qameta.allure.Description;
import listeners.AttachmentListener;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import page.object.portal.cases.CaseDuplicationPage;
import page.object.portal.cases.HomePage;
import page.object.portal.cases.LoginPage;
import page.object.portal.cases.ModalPage;
import page.object.portal.models.Episode;
import utils.DateTimeUtil;

import static enums.DuplicateOptions.getExpectedOptionsList;
import static enums.TagOps.BILLS;
import static enums.TagOps.LEGAL;
import static enums.Team.QA_TEAM;
import static org.assertj.core.api.Assertions.assertThat;
import static page.object.portal.cases.CaseDuplicationPage.getExpectedMediaList;
import static page.object.portal.models.Document.getDocumentsListOfTitles;
import static page.object.portal.models.Document.getExpectedDocumentsList;
import static utils.Config.*;
import static utils.DateTimeUtil.DATE_PATTERN_8;
import static utils.DateTimeUtil.DATE_PATTERN_9;
import static widgets.TagWidget.*;

@Listeners({AttachmentListener.class})
@Slf4j
public class SmokeTests extends BaseTest { //todo gradle test how to run with params
    private static final String COPIED_CASE_NAME = "Copy of John Smith";
    private static final int START_FROM_INDEX = 5;
    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final CaseDuplicationPage caseDuplicationPage = new CaseDuplicationPage();
    private final ModalPage modalPage = new ModalPage();

    @Description("Staple deletion")
    public void stapleDeletion() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .deleteStaple(START_FROM_INDEX);
    }

    @Description("Workspace Cleanup")
    public void workspaceCleanUp() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .deleteStaple(START_FROM_INDEX)
                .resetWorkspace();
    }

    @AfterGroups(description = "Delete episode", groups = "smoke", alwaysRun = true)
    public void episodeCleanUp() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .openPage(5)
                .deleteEpisode();
    }

    @Test(description = "Login user", groups = "smoke")
    @Description("Test Description: Login test")
    public void login() {
        loginPage.login(USER_NAME, USER_PASSWORD);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(loginPage.verifyLoggedInUserName())
                    .as("Wrong username is displaying after logged in")
                    .isEqualTo("Otto von Bismarck");

            softAssertions.assertThat(loginPage.getCurrentUrl())
                    .as("Wrong url is displaying after logged in")
                    .isEqualTo(BASE_URL + "/admin/cases");
        });
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

    @Test(description = "Stapling single-incident pages", groups = "stapling", dependsOnMethods = "login")
    @Description("Test Description: Stapling single-incident pages test with appending and detaching")
    public void verifyPagesStapling() {
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
        stapleDeletion();
        workspaceCleanUp();
    }

    @Test(description = "User should be able to add tags to the page", groups = "tagging", dependsOnMethods = "login")
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
        var episode = Episode.builder().build();
        var episode2 = Episode.builder()
                .author("Modified Author")
                .type("Modified type")
                .date(DateTimeUtil.tomorrowInFormat(DATE_PATTERN_8))
                .time(DateTimeUtil.timeInFormatPlus5(DATE_PATTERN_9))
                .build();

        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .openPage(5);
        modalPage
                .openCreateEpisodeForm()
                .fillInEpisodeForm(episode)
                .submit();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(episode);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.refreshPage();
        homePage.openPage(5).fillInEpisodeForm(episode2);

        assertThat(modalPage.parseEpisode())
                .as("Episode data should properly displayed")
                .isEqualTo(episode2);
    }

    @Test(description = "User should be able to assign page to the Workspace view", groups = "smoke", dependsOnMethods = "login")
    public void verifyAssigningPageToWorkspace() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .addPageToWorkspace(5)
                .openWorkspace();

        assertThat(homePage.getNumOfPagesInWorkspace()).as("").isEqualTo(5);
    }

    @Test(description = "User should be able to add notes to existing episodes")
    public void verifyPageNotation() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(getDocumentsListOfTitles())
                .openPage(1);
        modalPage
                .openCreateEpisodeForm()
                .fillInEpisodeForm(Episode.builder().build())
                .submit();
        modalPage.refreshPage();
        homePage.openPage(1);
    }
}
