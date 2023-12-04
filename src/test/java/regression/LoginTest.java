package regression;

import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static utils.Config.*;

public class LoginTest extends BaseAbstractTest {
    private final LoginPage loginPage = new LoginPage();

    @Test
    @Description("Verify that user is able to login in to the Case Portal")
    public void verifyLogin() {
        loginPage.login(USER_NAME, USER_PASSWORD);
//        verifyJSErrorInConsole();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(loginPage.verifyLoggedInUserName())
                    .as("Wrong username is displaying after logged in")
                    .isEqualTo("Otto von Bismarck");

            softAssertions.assertThat(loginPage.getCurrentUrl())
                    .as("Wrong url is displaying after logged in")
                    .isEqualTo(BASE_URL + "admin/cases");
        });
    }
}
