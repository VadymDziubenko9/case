package smoke;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static enums.TagOps.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;
import static widgets.TagWidget.*;

public class PageTagAdditionTest extends BaseAbstractTest {
    private static final String MISC_TAG = MISC.getName();
    private static final String LEGAL_TAG = LEGAL.getName();
    private static final String BILLS_TAG = BILLS.getName();

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify page tag addition for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyPageTagAdditionForRehabCenter() {
        MAIN_STAPLE_PAGE = 104;
        DOCUMENT_TITLE = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        openWorkspace();
        setPageTag(MAIN_STAPLE_PAGE, BILLS_TAG.toLowerCase(), DOCUMENT_TITLE);

        assertThat(getPageTagInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag %s should be shown on page card in workspace view".formatted(BILLS_TAG))
                .isEqualTo(BILLS_TAG);

        workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        assertThat(getPageTagInModalView())
                .as("Tag %s should be shown on page in modal view".formatted(BILLS_TAG))
                .isEqualTo(BILLS_TAG.toLowerCase());
    }

    @Test
    @Description("Verify page tag addition for 13153612.pdf document")
    public void verifyPageTagAdditionFor13153612() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = DOCUMENT_13153612_PDF.getTitle();
        openWorkspace();
        setPageTag(MAIN_STAPLE_PAGE, LEGAL_TAG.toLowerCase(), DOCUMENT_TITLE);

        assertThat(getPageTagInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag %s should be shown on page card in workspace view".formatted(LEGAL_TAG))
                .isEqualTo(LEGAL_TAG);

        workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        assertThat(getPageTagInModalView())
                .as("Tag %s should be shown on page in modal view".formatted(LEGAL_TAG))
                .isEqualTo(LEGAL_TAG.toLowerCase());
    }

    @Test
    @Description("Verify page tag addition for Smith Demo Fishing.pdf document")
    public void verifyPageTagAdditionForSmithFishing() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = SMITH_DEMO_FISHING_PDF.getTitle();
        openWorkspace();
        setPageTag(MAIN_STAPLE_PAGE, MISC_TAG.toLowerCase(), DOCUMENT_TITLE);

        assertThat(getPageTagInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag %s should be shown on page card in workspace view".formatted(MISC_TAG))
                .isEqualTo(MISC_TAG);

        workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        assertThat(getPageTagInModalView())
                .as("Tag %s should be shown on page in modal view".formatted(MISC_TAG))
                .isEqualTo(MISC_TAG.toLowerCase());
    }

    @Test
    @Description("Verify page tag addition for Robert Chase - 03.31.22.pdf document")
    public void verifyPageTagAdditionForRobertChase() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = ROBERT_CHASE_PDF.getTitle();
        openWorkspace();
        setPageTag(MAIN_STAPLE_PAGE, LEGAL_TAG.toLowerCase(), DOCUMENT_TITLE);

        assertThat(getPageTagInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag %s should be shown on page card in workspace view".formatted(LEGAL_TAG))
                .isEqualTo(LEGAL_TAG);

        workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        assertThat(getPageTagInModalView())
                .as("Tag %s should be shown on page in modal view".formatted(LEGAL_TAG))
                .isEqualTo(LEGAL_TAG.toLowerCase());
    }

    @Test
    @Description("Verify page tag addition for John Smith - 3.29.22_v2.pdf document")
    public void verifyPageTagAdditionForJohnSmith() {
        MAIN_STAPLE_PAGE = 1;
        DOCUMENT_TITLE = JOHN_SMITH_V_2_PDF.getTitle();
        openWorkspace();
        setPageTag(MAIN_STAPLE_PAGE, LEGAL_TAG.toLowerCase(), DOCUMENT_TITLE);

        assertThat(getPageTagInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag %s should be shown on page card in workspace view".formatted(LEGAL_TAG))
                .isEqualTo(LEGAL_TAG);

        workspacePage.openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        assertThat(getPageTagInModalView())
                .as("Tag %s should be shown on page in modal view".formatted(LEGAL_TAG))
                .isEqualTo(LEGAL_TAG.toLowerCase());
    }

    @Step("Open Workspace view")
    public void openWorkspace() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace();
    }
}
