package advanced.md;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

public class LoginPageAdvancedMd {
    private static final SelenideElement loginFrameLoc = $x("//*[@id='frame-login']");
    private static final SelenideElement loginEmailInput = $x("//form[@name='formLogin']//input[@name='loginname']");
    private static final SelenideElement loginPswInput = $x("//form[@name='formLogin']//input[@name='password']");
    private static final SelenideElement loginOfficeKeyInput = $x("//form[@name='formLogin']//input[@name='officeKey']");
    private static final SelenideElement submitLoginBtn = $x("//form[@name='formLogin']//button[@type='submit']");

    public HomePageAdvancedMd login() {
        switchTo().frame(loginFrameLoc);
        loginEmailInput.shouldBe(visible).sendKeys("MPINSON");
        loginPswInput.shouldBe(visible).sendKeys("K1[_Kia;");
        loginOfficeKeyInput.shouldBe(visible).sendKeys("142406");
        submitLoginBtn.shouldBe(enabled).click();
        return new HomePageAdvancedMd().waitUntilHomePageIsOpened();
    }
}
