package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import utils.JsUtil;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.Config.BASE_URL;

@Slf4j
public class LoginPage extends BaseAbstractPage{
    private final SelenideElement emailLoc = $x("//input[@id='username']");
    private final SelenideElement passwordLoc = $x("//input[@id='password']");
    private final SelenideElement logInButton = $x("//button[@data-action-button='logInForm']");
    private final SelenideElement contextMenuUserNameLoc = $x("//div[contains(@class,'MuiDrawer-paper')]//h6");
    private final SelenideElement userAvatarLocator = $x("//header//div[contains(@class,'MuiAvatar')]");


    @Step("Login to Case Chronology with credentials: {0}\n {1},  fot method: {method} step...")
    public HomePage login(String email, String password) {
        log.info("User logging with\n email: {} \n password: {} ", email, password);
        open(BASE_URL);
        JsUtil.waitForDomToLoad();
        emailLoc.sendKeys(email);
        passwordLoc.sendKeys(password);
        logInButton.shouldBe(enabled).click();
        JsUtil.waitForDomToLoad();
        return new HomePage().verifyIsUserLoadedIn();
    }

    @Step("Verify the name of the user logged in")
    public String getLoggedInUserName() {
        userAvatarLocator.shouldBe(visible).click();
        return contextMenuUserNameLoc.shouldBe(visible).getText();
    }

    @Step("Verify the url after logging")
    public String getCurrentUrl() {
        return getWebDriver().getCurrentUrl();
    }
}
