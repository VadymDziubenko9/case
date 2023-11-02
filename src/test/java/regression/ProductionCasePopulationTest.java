package regression;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import page.object.portal.cases.*;
import page.object.portal.models.Episode;

import static data.DocumentConstants.*;
import static data.EpisodeConstants.*;
import static enums.DuplicateOptions.getExpectedOptionsList;
import static enums.TagOps.BILLS;
import static enums.TagOps.LEGAL;
import static enums.Team.QA_TEAM;
import static org.assertj.core.api.Assertions.assertThat;
import static page.object.portal.cases.CaseDuplicationPage.getExpectedMediaList;
import static page.object.portal.models.PageCard.getPageCard;
import static utils.Config.*;
import static widgets.TagWidget.*;

@Slf4j
public class ProductionCasePopulationTest extends BaseTest { //todo gradle test how to run with params
    private static final String COPIED_CASE_NAME = "Copy of John Smith";
    private static int MAIN_STAPLE_PAGE;
    private static String DOCUMENT_TITLE;
    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final CaseDuplicationPage caseDuplicationPage = new CaseDuplicationPage();
    private final ModalPage modalPage = new ModalPage();
    private final WorkspacePage workspacePage = new WorkspacePage();

//    @Description("Staple deletion")
//    public void stapleDeletion() {
//        homePage
//                .openHomePage()
//                .openCase(COPIED_CASE_NAME)
//                .openDocument(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle())
//                .deleteStaple(START_FROM_INDEX);
//    }

//    @Description("Workspace Cleanup")
//    public void workspaceCleanUp() {
//        homePage
//                .openHomePage()
//                .openCase(COPIED_CASE_NAME)
//                .openDocument(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle())
//                .deleteStaple(START_FROM_INDEX)
//                .resetWorkspace();
//    }

//    @AfterGroups(description = "Delete episode", groups = "smoke")
//    public void episodeCleanUp() {
//        homePage
//                .openHomePage()
//                .openCase(COPIED_CASE_NAME)
//                .openDocument(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle())
//                .openPage(5)
//                .deleteEpisode();
//    }

    @Step("Create page episode")
    public void createEpisode(String documentTitle, Episode episode, int number) {
        log.info("Create {1} episode on {3} page number and for {0} document");
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentTitle)
                .openPage(number)
                .openCreateEpisodeForm()
                .fillInEpisodeForm(episode)
                .submit();
    }

    @Step("Open page in Workspace view")
    public void openPageInWorkspace(String documentTitle, int pageNumber) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace()
                .openPageCardInWorkspace(documentTitle, pageNumber);
    }

    @Test(description = "Login user", groups = "smoke")
    @Description("Test Description: Login test")
    public void login() {
        loginPage.login(USER_NAME, USER_PASSWORD);
        verifyJSErrorInConsole();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(loginPage.verifyLoggedInUserName())
                    .as("Wrong username is displaying after logged in")
                    .isEqualTo("Otto von Bismarck");

            softAssertions.assertThat(loginPage.getCurrentUrl())
                    .as("Wrong url is displaying after logged in")
                    .isEqualTo(BASE_URL + "/admin/cases");
        });
    }

    @Test(description = "Duplication of an existing case scenario", dependsOnMethods = "login", groups = "smoke")
    @Description("Test Description: Test for duplication of an existing case")
    public void verifyDuplicateCase() {
        var parsedDocuments = homePage
                .openHomePage()
                .openCase("John Smith")
                .getParsedDocuments();

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
                .as("Documents in the main case and in the copied case aren't matched")
                .containsExactlyInAnyOrderElementsOf(parsedCopiedDocuments);
    }

    @Test(description = "Stapling single-incident pages", groups = "stapling", dependsOnMethods = "login")
    @Description("Test Description: Stapling single-incident pages test")
    public void stapleSingleIncidentPagesTest() {
        SoftAssertions.assertSoftly(softAssertions -> {
            homePage
                    .openHomePage()
                    .openCase(COPIED_CASE_NAME)
                    .openDocument(DOCUMENT_13153612_PDF.getTitle())
                    .staplePages(1, 52);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(1))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(51);

            homePage
                    .openDocument(BESTSIDE_MEDICAL_GROUP_PDF.getTitle())
                    .staplePages(5, 12);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(5))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(7);

            homePage
                    .openDocument(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle())
                    .staplePages(102, 104)
                    .staplePages(104, 107);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(102))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(2);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(104))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(3);

            homePage
                    .openDocument(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle())
                    .staplePages(1, 3)
                    .staplePages(4, 8)
                    .staplePages(9, 14)
                    .staplePages(15, 20)
                    .staplePages(21, 24);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(1))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(2);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(4))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(4);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(9))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(5);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(15))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(5);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(21))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(3);

            homePage
                    .openDocument(ROBERT_CHASE_PDF.getTitle())
                    .staplePages(1, 33);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(1))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(32);

            homePage
                    .openDocument(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle())
                    .staplePages(26, 29)
                    .staplePages(90, 93);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(26))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(3);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(90))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(3);

            homePage
                    .openDocument(JOHN_SMITH_V_2_PDF.getTitle())
                    .staplePages(1, 70);

            softAssertions.assertThat(homePage.getNumOfPagesInStaple(1))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(69);
        });
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: document_13153612_pdf")
    public void episodeDataPopulationTest_1() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(DOCUMENT_13153612_PDF.getTitle(), EPISODE_13153612, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(EPISODE_13153612);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: bestsideMedicalGroupPdf document")
    public void episodeDataPopulationTest_2() {
        MAIN_STAPLE_PAGE = 5;
        createEpisode(BESTSIDE_MEDICAL_GROUP_PDF.getTitle(), BEST_SIDE_MEDICAL_GROUP_EPISODE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(BEST_SIDE_MEDICAL_GROUP_EPISODE);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: centralBayMedicalAndRehabCenterPdf document")
    public void episodeDataPopulationTest_3() {
        MAIN_STAPLE_PAGE = 102;
        createEpisode(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle(), CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();

        MAIN_STAPLE_PAGE = 104;
        createEpisode(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle(), CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_2, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_2);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: interventionalPainExpertsMRPdf document")
    public void episodeDataPopulationTest_4() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle(), PAIN_EXPERTS_MR_EPISODE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();

        MAIN_STAPLE_PAGE = 4;
        createEpisode(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle(), PAIN_EXPERTS_MR_EPISODE_2, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_2);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();

        MAIN_STAPLE_PAGE = 9;
        createEpisode(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle(), PAIN_EXPERTS_MR_EPISODE_3, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_3);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();

        MAIN_STAPLE_PAGE = 15;
        createEpisode(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle(), PAIN_EXPERTS_MR_EPISODE_4, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_4);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();

        MAIN_STAPLE_PAGE = 21;
        createEpisode(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle(), PAIN_EXPERTS_MR_EPISODE_5, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_5);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });

        modalPage.closeModalView();
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: robertChasePdf document")
    public void episodeDataPopulationTest_5() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(ROBERT_CHASE_PDF.getTitle(), ROBERT_CHASE_EPISODE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(ROBERT_CHASE_EPISODE);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });

        modalPage.closeModalView();
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: strongHealthCarrollwoodPdf document")
    public void episodeDataPopulationTest_6() {
        MAIN_STAPLE_PAGE = 26;
        createEpisode(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle(), STRONG_HEALTH_EPISODE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_EPISODE);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });

        modalPage.closeModalView();

        MAIN_STAPLE_PAGE = 90;
        createEpisode(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle(), STRONG_HEALTH_EPISODE_2, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_EPISODE_2);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });

        modalPage.closeModalView();
    }

    @Test(priority = 1,
            description = "User should be able to create and populate episode with date, author and type data",
            groups = "smoke",
            dependsOnMethods = "login")
    @Description("Test description: johnSmithV2Pdf document")
    public void episodeDataPopulationTest_7() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(JOHN_SMITH_V_2_PDF.getTitle(), JOHN_SMITH_EPISODE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(JOHN_SMITH_EPISODE);

            softAssertions.assertThat(modalPage.isEpisodeMarkedAsParent())
                    .as("Episode should be marked as parent")
                    .isTrue();

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
        });
        modalPage.closeModalView();
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: 13153612.pdf document")
    public void episodeNotesPopulationTest_1() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = DOCUMENT_13153612_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(EPISODE_13153612)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(EPISODE_13153612.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: Bestside Medical Group.PDF document")
    public void episodeNotesPopulationTest_2() {
        MAIN_STAPLE_PAGE = 5;
        DOCUMENT_TITLE = BESTSIDE_MEDICAL_GROUP_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(BEST_SIDE_MEDICAL_GROUP_EPISODE)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(BEST_SIDE_MEDICAL_GROUP_EPISODE.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: Central Bay Medical and Rehab Center (106).PDF document")
    public void episodeNotesPopulationTest_3() {
        MAIN_STAPLE_PAGE = 102;
        DOCUMENT_TITLE = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: Interventional Pain ExpertsMR.pdf document")
    public void episodeNotesPopulationTest_4() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(PAIN_EXPERTS_MR_EPISODE)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });

        MAIN_STAPLE_PAGE = 4;
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(PAIN_EXPERTS_MR_EPISODE_2)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_2.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });

        MAIN_STAPLE_PAGE = 9;
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(PAIN_EXPERTS_MR_EPISODE_3)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_3.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });

        MAIN_STAPLE_PAGE = 15;
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(PAIN_EXPERTS_MR_EPISODE_4)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_4.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });

        MAIN_STAPLE_PAGE = 21;
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(PAIN_EXPERTS_MR_EPISODE_5)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_5.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: Robert Chase - 03.31.22.pdf document")
    public void episodeNotesPopulationTest_5() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = ROBERT_CHASE_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(ROBERT_CHASE_EPISODE)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(ROBERT_CHASE_EPISODE.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: StrongHealth Carrollwood (138).pdf document")
    public void episodeNotesPopulationTest_6() {
        MAIN_STAPLE_PAGE = 26;
        DOCUMENT_TITLE = STRONG_HEALTH_CARROLLWOOD_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(STRONG_HEALTH_EPISODE)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(STRONG_HEALTH_EPISODE.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });

        MAIN_STAPLE_PAGE = 90;
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(STRONG_HEALTH_EPISODE_2)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(STRONG_HEALTH_EPISODE_2.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description: John Smith - 3.29.22_v2.pdf document")
    public void episodeNotesPopulationTest_7() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = JOHN_SMITH_V_2_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("")
                    .isEqualTo(DOCUMENT_TITLE);

            modalPage
                    .episodeNotes(JOHN_SMITH_EPISODE)
                    .saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("")
                    .isEqualTo(JOHN_SMITH_EPISODE.getNotes());

            modalPage.closeModalView();
            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("")
                    .isTrue();
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description")
    public void setPageCardColorTest() {
        var color = "red";
        var color2 = "blueGrey";
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = DOCUMENT_13153612_PDF.getTitle();

        SoftAssertions.assertSoftly(softAssertions -> {
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            workspacePage.setModalPageColor(color);
            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed")
                    .isEqualTo(color);

            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed")
                    .isEqualTo(color);

            workspacePage
                    .openPageColorDialog(DOCUMENT_TITLE, MAIN_STAPLE_PAGE)
                    .setPageColor(color2);

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed")
                    .isEqualTo(color2);

            workspacePage.openPageCardInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed")
                    .isEqualTo(color2);
        });
    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description")
    public void setPageCardTagTest() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = DOCUMENT_13153612_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setPageTagOnModalView(LEGAL.getName().toLowerCase());

        modalPage.closeModalView();

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(LEGAL.getName());

        setPageTag(MAIN_STAPLE_PAGE, BILLS.getName().toLowerCase(), DOCUMENT_TITLE);

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(BILLS.getName());

    }

    @Test(groups = "smoke", dependsOnMethods = "login")
    @Description("Test description")
    public void filterPagesByEpisodeTypeTest() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace()
                .filterPagesByEpisodeType(EPISODE_13153612.getType());
        var pageCard = workspacePage.parseWorkspacePages();

        assertThat(pageCard).as("").hasSize(1);
        assertThat(pageCard.get(0).getDocumentTitle()).as("").isEqualTo(DOCUMENT_13153612_PDF.getTitle());
//        assertThat(pageCard.get(1).getDocumentTitle()).as("").isEqualTo(robertChasePdf.getTitle());
//        assertThat(pageCard.get(2).getDocumentTitle()).as("").isEqualTo(johnSmithV2Pdf.getTitle());

        workspacePage.filterPagesByEpisodeType(BEST_SIDE_MEDICAL_GROUP_EPISODE.getType());
        pageCard = workspacePage.parseWorkspacePages();
        assertThat(pageCard).as("").hasSize(2);

        assertThat(pageCard.get(0).getDocumentTitle()).as("").isEqualTo(BESTSIDE_MEDICAL_GROUP_PDF.getTitle());
        assertThat(pageCard.get(0).getDate()).as("").isEqualTo(BEST_SIDE_MEDICAL_GROUP_EPISODE.getDate());

        assertThat(pageCard.get(1).getDocumentTitle()).as("").isEqualTo(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle());
        assertThat(pageCard.get(1).getDate()).as("").isEqualTo(PAIN_EXPERTS_MR_EPISODE_5.getDate());
    }

    @Test()
    @Description("Test description")
    public void filterPagesByEpisodeAuthorTest() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace()
                .filterPagesByAuthorType(EPISODE_13153612.getAuthor());

        var pageCard = workspacePage.parseWorkspacePages();
        assertThat(pageCard).as("").hasSize(1)
                .as("")
                .isEqualTo(getPageCard(DOCUMENT_13153612_PDF.getTitle(), EPISODE_13153612.getDate(), EPISODE_13153612.getTime()));
    }

    @Test()
    @Description("Test description")
    public void filterPagesByColorTest() {
    }

    @Test()
    @Description("Test description")
    public void filterPagesByTagTest() {
    }

    @Test(description = "User should be able to assign page to the Workspace view", groups = "smoke", dependsOnMethods = "login")
    public void verifyAssignedPagesToWorkspace() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle())
                .addPageToWorkspace(5)
                .openWorkspace();

        assertThat(homePage.getNumOfPagesInWorkspace()).as("").isEqualTo(5);
    }
}
