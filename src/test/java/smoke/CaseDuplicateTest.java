package smoke;

import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.CaseDuplicationPage;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.getDocumentsListOfTitles;
import static constants.DocumentConstants.getExpectedDocumentsList;
import static enums.DuplicateOption.getExpectedOptionsList;
import static enums.Team.QA_TEAM;
import static org.assertj.core.api.Assertions.assertThat;
import static page.object.portal.cases.CaseDuplicationPage.getExpectedMediaList;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;

@Slf4j
public class CaseDuplicateTest extends BaseAbstractTest {
    private final CaseDuplicationPage caseDuplicationPage = new CaseDuplicationPage();

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    @Description("Verify case duplication for an existing case")
    public void verifyCaseDuplicate() {
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
}
