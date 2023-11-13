package regression;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static data.DocumentConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;

public class PageStaplingTest extends BaseAbstractTest {

    @Step("Open {0} document and staple pages from {1} to {2}")
    public void staplePages(String documentTitle, int fromPage, int ToPage) {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openDocument(documentTitle)
                .staplePages(fromPage, ToPage);
    }

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify page stapling for 13153612.pdf document")
    public void verifyPageStaplingFor13153612() {
        staplePages(DOCUMENT_13153612_PDF.getTitle(), 1, 52);

        assertThat(homePage.getNumOfPagesInStaple(1))
                .as("The incorrect number of stapled pages is being displayed")
                .isEqualTo(51);
    }

    @Test
    @Description("Verify page stapling for Bestside Medical Group.PDF document")
    public void verifyPageStaplingForMedicalGroup() {
        staplePages(BESTSIDE_MEDICAL_GROUP_PDF.getTitle(), 5, 12);

        assertThat(homePage.getNumOfPagesInStaple(5))
                .as("The incorrect number of stapled pages is being displayed")
                .isEqualTo(7);
    }

    @Test
    @Description("Verify page stapling for Central Bay Medical and Rehab Center (106).PDF document")
    public void verifyPageStaplingForAndRehabCenter() {
        homePage
                .openDocument(CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF.getTitle())
                .staplePages(102, 104)
                .staplePages(104, 107);

        assertThat(homePage.getNumOfPagesInStaple(102))
                .as("The incorrect number of stapled pages is being displayed")
                .isEqualTo(2);
        assertThat(homePage.getNumOfPagesInStaple(104))
                .as("The incorrect number of stapled pages is being displayed")
                .isEqualTo(3);
    }

    @Test
    @Description("Verify page stapling for Interventional Pain ExpertsMR.pdf document")
    public void verifyPageStaplingForPainExpertsMR() {
        homePage
                .openDocument(INTERVENTIONAL_PAIN_EXPERTS_MR_PDF.getTitle())
                .staplePages(1, 3)
                .staplePages(4, 8)
                .staplePages(9, 14)
                .staplePages(15, 20)
                .staplePages(21, 24);

        SoftAssertions.assertSoftly(softAssertions -> {
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
        });
    }

    @Test
    @Description("Verify page stapling for Robert Chase - 03.31.22.pdf document")
    public void verifyPageStaplingForRobertChase() {
        staplePages(ROBERT_CHASE_PDF.getTitle(), 1, 33);

        assertThat(homePage.getNumOfPagesInStaple(1))
                .as("The incorrect number of stapled pages is being displayed")
                .isEqualTo(32);
    }

    @Test
    @Description("Verify page stapling for StrongHealth Carrollwood (138).pdf document")
    public void verifyPageStaplingForCarrollWood() {
        homePage
                .openDocument(STRONG_HEALTH_CARROLLWOOD_PDF.getTitle())
                .staplePages(12, 16)
                .staplePages(26, 29)
                .staplePages(76, 80)
                .staplePages(90, 93);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(12))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(4);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(26))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(3);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(76))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(4);
            softAssertions.assertThat(homePage.getNumOfPagesInStaple(90))
                    .as("The incorrect number of stapled pages is being displayed")
                    .isEqualTo(3);
        });
    }

    @Test
    @Description("Verify page stapling for John Smith - 3.29.22_v2.pdf document")
    public void verifyPageStaplingForJohnSmith() {
        staplePages(JOHN_SMITH_V_2_PDF.getTitle(), 1, 70);

        assertThat(homePage.getNumOfPagesInStaple(1))
                .as("The incorrect number of stapled pages is being displayed")
                .isEqualTo(69);
    }
}
