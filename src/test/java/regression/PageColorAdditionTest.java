package regression;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;

public class PageColorAdditionTest extends BaseAbstractTest {
    private static final String RED_COLOR = "red";

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify page color addition for StrongHealth Carrollwood (138).pdf document")
    public void verifyPageColorAdditionForCarrolWood() {
        DOCUMENT_TITLE = STRONG_HEALTH_CARROLLWOOD_PDF.getTitle();
        MAIN_STAPLE_PAGE = 12;
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        workspacePage.setModalPageColor(RED_COLOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed on Modal view")
                    .isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
            MAIN_STAPLE_PAGE = 76;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            workspacePage.setModalPageColor(RED_COLOR);

            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed on Modal view")
                    .isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
            MAIN_STAPLE_PAGE = 104;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            workspacePage.setModalPageColor(RED_COLOR);

            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed on Modal view")
                    .isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
        });
    }

    @Test
    @Description("Verify page color addition for Bestside Medical Group.PDF document")
    public void verifyPageColorAdditionForMedicalGroup() {
        MAIN_STAPLE_PAGE = 14;
        DOCUMENT_TITLE = BESTSIDE_MEDICAL_GROUP_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        workspacePage.setModalPageColor(RED_COLOR);

        assertThat(workspacePage.getModalPageColor())
                .as("Color is not displayed on Modal view")
                .isEqualTo(RED_COLOR);

        modalPage.closeModalView();
        assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Color is not displayed on File view");

    }

    @Test
    @Description("Verify page color addition for Smith Demo Fishing.pdf document")
    public void verifyPageColorAdditionForFishing() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = SMITH_DEMO_FISHING_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        workspacePage.setModalPageColor(RED_COLOR);

        assertThat(workspacePage.getModalPageColor())
                .as("Color is not displayed on Modal view")
                .isEqualTo(RED_COLOR);
        modalPage.closeModalView();

        assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Color is not displayed on File view")
                .isEqualTo(RED_COLOR);
    }

    @Test
    @Description("Verify page color addition for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyPageColorAdditionForRehabCenter() {
        MAIN_STAPLE_PAGE = 8;
        DOCUMENT_TITLE = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        workspacePage.setModalPageColor(RED_COLOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed on Modal view")
                    .isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
            MAIN_STAPLE_PAGE = 47;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            workspacePage.setModalPageColor(RED_COLOR);

            softAssertions.assertThat(workspacePage.getModalPageColor())
                    .as("Color is not displayed on Modal view")
                    .isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(workspacePage.getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
        });
    }

    @Step("Open {1} page in Workspace view")
    public void openPageInWorkspace(String documentTitle, int pageNumber) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace()
                .openPageCardInWorkspace(documentTitle, pageNumber);
    }

}
