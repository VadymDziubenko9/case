package regression;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static constants.EpisodeConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;

public class EpisodeNotesAdditionTest extends BaseAbstractTest {

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test(description = "Verify episode notes addition for 13153612.pdf document")
    @Description("Verify episode notes addition for 13153612.pdf document")
    public void verifyEpisodeNotesAdditionFor13153612() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = DOCUMENT_13153612_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(EPISODE_NOTES_13153612).saveEpisodeOnEdit();

        assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(EPISODE_NOTES_13153612.getNotes());
            modalPage.closeModalView();

        assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
    }

    @Test
    @Description("Verify episode notes addition for Bestside Medical Group.PDF document")
    public void verifyEpisodeNotesAdditionForMedicalGroup() {
        MAIN_STAPLE_PAGE = 5;
        DOCUMENT_TITLE = BESTSIDE_MEDICAL_GROUP_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(BEST_SIDE_MEDICAL_GROUP_EPISODE_NOTES).saveEpisodeOnEdit();

        assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(BEST_SIDE_MEDICAL_GROUP_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

        assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
    }

    @Test
    @Description("Verify episode notes addition for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyEpisodeNotesAdditionForRehabCenter() {
        MAIN_STAPLE_PAGE = 8;
        DOCUMENT_TITLE = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_NOTES).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 47;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_NOTES_2).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_NOTES_2.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 102;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_NOTES).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 104;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_NOTES_2).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_NOTES_2.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
        });

    }

    @Test
    @Description("Verify episode notes addition for Interventional Pain ExpertsMR.pdf document")
    public void verifyEpisodeNotesAdditionForPainExpertsMR() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(PAIN_EXPERTS_MR_EPISODE_NOTES).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 4;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(PAIN_EXPERTS_MR_EPISODE_NOTES_2).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_NOTES_2.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 9;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(PAIN_EXPERTS_MR_EPISODE_NOTES_3).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_NOTES_3.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 15;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(PAIN_EXPERTS_MR_EPISODE_NOTES_4).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_NOTES_4.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 21;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(PAIN_EXPERTS_MR_EPISODE_NOTES_5).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_NOTES_5.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
        });
    }

    @Test
    @Description("Verify episode notes addition for Robert Chase - 03.31.22.pdf document")
    public void verifyEpisodeNotesAdditionForRobertChase() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = ROBERT_CHASE_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(ROBERT_CHASE_EPISODE_NOTES).saveEpisodeOnEdit();

        assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(ROBERT_CHASE_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

        assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
    }

    @Test
    @Description("Verify episode notes addition for StrongHealth Carrollwood (138).pdf document")
    public void verifyEpisodeNotesAdditionForCarrolWood() {
        MAIN_STAPLE_PAGE = 12;
        DOCUMENT_TITLE = STRONG_HEALTH_CARROLLWOOD_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(STRONG_HEALTH_EPISODE_NOTES_4).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(STRONG_HEALTH_EPISODE_NOTES_4.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 26;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(STRONG_HEALTH_EPISODE_NOTES).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(STRONG_HEALTH_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 76;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(STRONG_HEALTH_EPISODE_NOTES_3).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(STRONG_HEALTH_EPISODE_NOTES_3.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
            MAIN_STAPLE_PAGE = 90;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            modalPage.fillEpisodeNotes(STRONG_HEALTH_EPISODE_NOTES_2).saveEpisodeOnEdit();

            softAssertions.assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(STRONG_HEALTH_EPISODE_NOTES_2.getNotes());
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
        });
    }

    @Test
    @Description("Verify episode notes addition for John Smith - 3.29.22_v2.pdf document")
    public void verifyEpisodeNotesAdditionForJohnSmith() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = JOHN_SMITH_V_2_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

        assertThat(workspacePage.getModalViewDocumentTitle())
                    .as("Document isn't opened or wrong one is opened")
                    .isEqualTo(DOCUMENT_TITLE);
            modalPage.fillEpisodeNotes(JOHN_SMITH_EPISODE_NOTES).saveEpisodeOnEdit();

        assertThat(modalPage.parseEpisode().getNotes())
                    .as("Episode notes don't match")
                    .isEqualTo(JOHN_SMITH_EPISODE_NOTES.getNotes());
            modalPage.closeModalView();

        assertThat(workspacePage.isNotesIconAppearedOnPageCard(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Notes Icon on page card doesn't appear")
                    .isTrue();
    }

    @Step("Open page in Workspace view")
    public void openPageInWorkspace(String documentTitle, int pageNumber) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace()
                .openPageCardInWorkspace(documentTitle, pageNumber);
    }
}
