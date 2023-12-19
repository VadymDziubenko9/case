package smoke;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;

public class PageAdditionToWorkspaceTest extends BaseAbstractTest {

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify page addition in to the Workspace for StrongHealth Carrollwood (138).pdf document")
    public void verifySinglePageAdditionToWorkspaceForCarrollWood() {
        addPageToWorkspace(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle(), 8);
        homePage
                .openWorkspace()
                .openPageInWorkspace(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle(), 8);

        assertThat(modalPage.isIncludePageIntoWorkspaceSelected())
                .as("Include page in to workspace toggle unchecked")
                .isTrue();

        addPageToWorkspace(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle(), 12);
        homePage
                .openWorkspace()
                .openPageInWorkspace(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle(), 12);

        assertThat(modalPage.isIncludePageIntoWorkspaceSelected())
                .as("Include page in to workspace toggle unchecked")
                .isTrue();
    }

    @Test
    @Description("Verify page addition in to the Workspace for Bestside Medical Group.PDF document")
    public void verifySinglePageAdditionToWorkspaceForMedicalGroup() {
        addPageToWorkspace(BESTSIDE_MEDICAL_GROUP_PDF.getTitle(), 14);
        homePage
                .openWorkspace()
                .openPageInWorkspace(BESTSIDE_MEDICAL_GROUP_PDF.getTitle(), 14);

        assertThat(modalPage.isIncludePageIntoWorkspaceSelected())
                .as("Include page in to workspace toggle unchecked")
                .isTrue();
    }

    @Test
    @Description("Verify page addition in to the Workspace for Smith Demo Fishing.pdf document")
    public void verifySinglePageAdditionToWorkspaceForFishing() {
        addPageToWorkspace(SMITH_DEMO_FISHING_PDF.getTitle(), 1);
        homePage
                .openWorkspace()
                .openPageInWorkspace(SMITH_DEMO_FISHING_PDF.getTitle(), 1);

        assertThat(modalPage.isIncludePageIntoWorkspaceSelected())
                .as("Include page in to workspace toggle unchecked")
                .isTrue();
    }

    @Test
    @Description("Verify page addition in to the Workspace for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifySinglePageAdditionToWorkspaceForRehabCenter() {
        addPageToWorkspace(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle(), 8);
        homePage
                .openWorkspace()
                .openPageInWorkspace(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle(), 8);

        assertThat(modalPage.isIncludePageIntoWorkspaceSelected())
                .as("Include page in to workspace toggle unchecked")
                .isTrue();
        addPageToWorkspace(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle(), 47);
        homePage
                .openWorkspace()
                .openPageInWorkspace(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle(), 47);

        assertThat(modalPage.isIncludePageIntoWorkspaceSelected())
                .as("Include page in to workspace toggle unchecked")
                .isTrue();

    }

    @Step("Open {1} page in Workspace view")
    private void addPageToWorkspace(String documentTitle, int pageNum) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentTitle)
                .addPageToWorkspace(pageNum);
    }
}
