package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.Config.BASE_URL;

@Slf4j
public class LoginPage {

    private final SelenideElement emailLoc = $x("//input[@id='username']");
    private final SelenideElement passwordLoc = $x("//input[@id='password']");
    private final SelenideElement logInButton = $x("//button[@data-action-button='logInForm']");
    private final SelenideElement userNameIconLoc = $x("//div[contains(@class,'MuiButtonBase-root')]//span[contains(@class,'MuiChip-label')]");

    @Step("Login to Case Chronology with credentials: {0}/ {1},  fot method: {method} step...")
    public LoginPage login(String email, String password) {
        log.info("User logging with\n email: {} \n password: {} ", email, password);
        open(BASE_URL);
        emailLoc.sendKeys(email);
        passwordLoc.sendKeys(password);
        logInButton.shouldBe(enabled).click();
        return this;
    }

    @Step("Verify the name of the user logged in {0}")
    public String verifyLoggedInUserName(){
        return userNameIconLoc.getText();
    }

    @Step("Verify the url after logging in {0}")
    public String getCurrentUrl(){
        return getWebDriver().getCurrentUrl();
    }
}
