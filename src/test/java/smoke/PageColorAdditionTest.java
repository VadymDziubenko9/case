package smoke;

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
import static widgets.ColorWidget.*;

public class PageColorAdditionTest extends BaseAbstractTest {
    private static final String RED_COLOR = "red";
    private static final String BLUE_GREY_COLOR = "blueGrey";

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
        setModalPageColor(RED_COLOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
            MAIN_STAPLE_PAGE = 26;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            setModalPageColor(RED_COLOR);

            softAssertions.assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on File view")
                    .isEqualTo(RED_COLOR);
            MAIN_STAPLE_PAGE = 76;
            openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
            setModalPageColor(RED_COLOR);

            softAssertions.assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);
            modalPage.closeModalView();

            softAssertions.assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
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
        setModalPageColor(RED_COLOR);

        assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);

        modalPage.closeModalView();
        assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE)).as("Color is not displayed on File view").isEqualTo(RED_COLOR);
    }

    @Test
    @Description("Verify page color addition for Smith Demo Fishing.pdf document")
    public void verifyPageColorAdditionForFishing() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = SMITH_DEMO_FISHING_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setModalPageColor(RED_COLOR);

        assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);
        modalPage.closeModalView();

        assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE)).as("Color is not displayed on File view").isEqualTo(RED_COLOR);
    }

    @Test
    @Description("Verify page color addition for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyPageColorAdditionForRehabCenterFromWorkspaceView() {
        MAIN_STAPLE_PAGE = 8;
        DOCUMENT_TITLE = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        openWorkspace();
        setPageColor(RED_COLOR, MAIN_STAPLE_PAGE, DOCUMENT_TITLE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on Workspace view")
                    .isEqualTo(RED_COLOR);
            workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

            softAssertions.assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 47;
            setPageColor(RED_COLOR, MAIN_STAPLE_PAGE, DOCUMENT_TITLE);

            softAssertions.assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on Workspace view")
                    .isEqualTo(RED_COLOR);
            workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

            softAssertions.assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(RED_COLOR);
            modalPage.closeModalView();
            MAIN_STAPLE_PAGE = 104;
            setPageColor(BLUE_GREY_COLOR, MAIN_STAPLE_PAGE, DOCUMENT_TITLE);

            softAssertions.assertThat(getPageColor(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                    .as("Color is not displayed on Workspace view")
                    .isEqualTo(BLUE_GREY_COLOR);
            workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);

            softAssertions.assertThat(getModalPageColor()).as("Color is not displayed on Modal view").isEqualTo(BLUE_GREY_COLOR);
            modalPage.closeModalView();
        });
    }

    @Step("Open {1} page {0} in Workspace view")
    public void openPageInWorkspace(String documentTitle, int pageNumber) {
        homePage.openHomePage().openCase(COPIED_CASE_NAME).openWorkspace().openPageInWorkspace(documentTitle, pageNumber);
    }

    @Step("Open Workspace view")
    public void openWorkspace() {
        homePage.openHomePage().openCase(COPIED_CASE_NAME).openWorkspace();
    }
}
