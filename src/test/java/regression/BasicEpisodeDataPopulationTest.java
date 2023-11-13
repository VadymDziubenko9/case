package regression;

import dto.Episode;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static constants.EpisodeConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;

@Slf4j
public class BasicEpisodeDataPopulationTest extends BaseAbstractTest {

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for 13153612.pdf document")
    public void verifyBasicEpisodeDataPopulationFor13153612() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(DOCUMENT_13153612_PDF.getTitle(), EPISODE_13153612, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(EPISODE_13153612);

        assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(EPISODE_13153612);

        assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for Bestside Medical Group.PDF document")
    public void verifyBasicEpisodeDataPopulationForMedicalGroup() {
        var documentTitle = BESTSIDE_MEDICAL_GROUP_PDF.getTitle();
        MAIN_STAPLE_PAGE = 5;
        createEpisode(documentTitle, BEST_SIDE_MEDICAL_GROUP_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(BEST_SIDE_MEDICAL_GROUP_EPISODE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(BEST_SIDE_MEDICAL_GROUP_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 14;
            createEpisode(documentTitle, BEST_SIDE_MEDICAL_GROUP_SINGLE_EPISODE, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(FISHING_SINGLE_EPISODE);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(BEST_SIDE_MEDICAL_GROUP_SINGLE_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
        });
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyBasicEpisodeDataPopulationForRehabCenter() {
        var documentTitle = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        MAIN_STAPLE_PAGE = 8;
        createEpisode(documentTitle, CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 47;
            createEpisode(documentTitle, CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_2, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_2);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_2);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 102;
            createEpisode(documentTitle, CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();

            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 104;
            createEpisode(documentTitle, CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_2, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_2);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_2);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
        });
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for Interventional Pain ExpertsMR.pdf document")
    public void verifyBasicEpisodeDataPopulationForPainExpertsMR() {
        var documentTitle = INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle();
        MAIN_STAPLE_PAGE = 1;
        createEpisode(documentTitle, PAIN_EXPERTS_MR_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(PAIN_EXPERTS_MR_EPISODE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 4;
            createEpisode(documentTitle, PAIN_EXPERTS_MR_EPISODE_2, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(PAIN_EXPERTS_MR_EPISODE_2);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_2);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 9;
            createEpisode(documentTitle, PAIN_EXPERTS_MR_EPISODE_3, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(PAIN_EXPERTS_MR_EPISODE_3);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_3);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 15;
            createEpisode(documentTitle, PAIN_EXPERTS_MR_EPISODE_4, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(PAIN_EXPERTS_MR_EPISODE_4);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_4);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 21;
            createEpisode(documentTitle, PAIN_EXPERTS_MR_EPISODE_5, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(PAIN_EXPERTS_MR_EPISODE_5);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(PAIN_EXPERTS_MR_EPISODE_5);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
        });
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for Robert Chase - 03.31.22.pdf document")
    public void verifyBasicEpisodeDataPopulationForRobertChase() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(ROBERT_CHASE_PDF.getTitle(), ROBERT_CHASE_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(ROBERT_CHASE_EPISODE);

        assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(ROBERT_CHASE_EPISODE);

        assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for StrongHealth Carrollwood (138).pdf document")
    public void verifyBasicEpisodeDataPopulationForCarrollWood() {
        var documentTitle = STRONG_HEALTH_CARROLLWOOD_PDF.getTitle();
        MAIN_STAPLE_PAGE = 8;
        createEpisode(documentTitle, STRONG_HEALTH_SINGLE_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(STRONG_HEALTH_SINGLE_EPISODE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_SINGLE_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 12;
            createEpisode(documentTitle, STRONG_HEALTH_EPISODE_4, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(STRONG_HEALTH_EPISODE_4);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_EPISODE_4);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 26;
            createEpisode(documentTitle, STRONG_HEALTH_EPISODE, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(STRONG_HEALTH_EPISODE);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_EPISODE);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 76;
            createEpisode(documentTitle, STRONG_HEALTH_EPISODE_2, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(STRONG_HEALTH_EPISODE_2);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_EPISODE_2);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 90;
            createEpisode(documentTitle, STRONG_HEALTH_EPISODE_3, MAIN_STAPLE_PAGE);
            modalPage.ifNotParentMarkAsParent(STRONG_HEALTH_EPISODE_3);

            softAssertions.assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(STRONG_HEALTH_EPISODE_3);

            softAssertions.assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
        });
    }

    @Test
    @Description("Verify Author, Type, Date and Time data population for John Smith - 3.29.22_v2.pdf document")
    public void verifyBasicEpisodeDataPopulationForJohnSmith() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(JOHN_SMITH_V_2_PDF.getTitle(), JOHN_SMITH_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(JOHN_SMITH_EPISODE);

        assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(JOHN_SMITH_EPISODE);

        assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
    }

    @Test(description = "Verify basic episode data population for Smith Demo Fishing.pdf document")
    @Description("Verify Author, Type, Date and Time data population for Smith Demo Fishing.pdf document")
    public void verifyBasicEpisodeDataPopulationForFishing() {
        MAIN_STAPLE_PAGE = 1;
        createEpisode(SMITH_DEMO_FISHING_PDF.getTitle(), FISHING_SINGLE_EPISODE, MAIN_STAPLE_PAGE);
        modalPage.ifNotParentMarkAsParent(FISHING_SINGLE_EPISODE);

        assertThat(modalPage.parseEpisode())
                    .as("Episode data should properly displayed")
                    .isEqualTo(FISHING_SINGLE_EPISODE);

        assertThat(modalPage.isPreEventToggleSelected())
                    .as("Episode shouldn't be selected if episode date bigger than event")
                    .isFalse();
            modalPage.closeModalView();
    }

    @Step("Create page {1} episode on {2} page in {0} document")
    private void createEpisode(String documentTitle, Episode episode, int number) {
        log.info("Create {1} episode on {2} page number and for {0} document");
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentTitle)
                .openPage(number)
                .openCreateEpisodeForm()
                .fillInEpisodeForm(episode)
                .saveEpisode();
    }
}
