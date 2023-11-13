package regression;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static enums.TagOps.BILLS;
import static enums.TagOps.LEGAL;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;
import static widgets.TagWidget.getDocumentPageTag;
import static widgets.TagWidget.setPageTagOnModalView;

public class PageTagAdditionTest extends BaseAbstractTest {
    private static final String LEGAL_LABEL = LEGAL.getName();
    private static int MAIN_STAPLE_PAGE = 1;

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify page tag addition for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyPageTagAdditionForRehabCenter() {
        MAIN_STAPLE_PAGE = 104;
        DOCUMENT_TITLE = CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setPageTagOnModalView(BILLS.getName().toLowerCase());
        modalPage.closeModalView();

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(BILLS.getName());
    }

    @Test
    @Description("Verify page tag addition for 13153612.pdf document")
    public void verifyPageTagAdditionFor13153612() {
        DOCUMENT_TITLE = DOCUMENT_13153612_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setPageTagOnModalView(LEGAL_LABEL.toLowerCase());
        modalPage.closeModalView();

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(LEGAL_LABEL);
    }

    @Test
    @Description("Verify page tag addition for John Davis , M.D. - 4.7.22.pdf document")
    public void verifyPageTagAdditionForJohnDavis() {
        DOCUMENT_TITLE = JOHN_DAVIS_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setPageTagOnModalView(LEGAL_LABEL.toLowerCase());
        modalPage.closeModalView();

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(LEGAL_LABEL);
    }

    @Test
    @Description("Verify page tag addition for Robert Chase - 03.31.22.pdf document")
    public void verifyPageTagAdditionForRobertChase() {
        DOCUMENT_TITLE = ROBERT_CHASE_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setPageTagOnModalView(LEGAL_LABEL.toLowerCase());
        modalPage.closeModalView();

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(LEGAL_LABEL);
    }

    @Test
    @Description("Verify page tag addition for John Smith - 3.29.22_v2.pdf document")
    public void verifyPageTagAdditionForJohnSmith() {
        DOCUMENT_TITLE = JOHN_SMITH_V_2_PDF.getTitle();
        openPageInWorkspace(DOCUMENT_TITLE, MAIN_STAPLE_PAGE);
        setPageTagOnModalView(LEGAL_LABEL.toLowerCase());
        modalPage.closeModalView();

        assertThat(getDocumentPageTag(DOCUMENT_TITLE, MAIN_STAPLE_PAGE))
                .as("Tag should be correctly shown on page card in the file view after edit")
                .isEqualTo(LEGAL_LABEL);
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
