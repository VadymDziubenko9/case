package smoke;

import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.EpisodeConstants.PAIN_EXPERTS_MR_EPISODE_3;
import static constants.EpisodeConstants.STRONG_HEALTH_EPISODE_3;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;
import static widgets.FilteringWidget.filteringByEpisodeTypes;

public class PagesFilteringTest extends BaseAbstractTest {
    private final String orthopaedicType = PAIN_EXPERTS_MR_EPISODE_3.getType();
    private final String xRayType = STRONG_HEALTH_EPISODE_3.getType();

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test
    public void verifyEpisodeTypesFiltering() {
        openWorkspace();
        filteringByEpisodeTypes(orthopaedicType);
    }

    @Step("Open Workspace view")
    public void openWorkspace() {
        homePage
                .openHomePage()
                .openCase(COPIED_CASE_NAME)
                .openWorkspace();
    }
}
