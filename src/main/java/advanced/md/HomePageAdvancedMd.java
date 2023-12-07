package advanced.md;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.AwaitUtil.awaitSafe;
import static utils.JsUtil.waitForDomToLoad;

@Slf4j
public class HomePageAdvancedMd {
    private final SelenideElement patientInfoBtn = $x("//header[@class='container-fluid']//div[contains(@class,'category') and .//@item-click='maintoolbar.openView(menuItem);']//i[@title='Patient Info']");
    private final ElementsCollection firstLookupElement = $$x("//div[@class='lookup-option-container']//mat-option//div[contains(@class,'option-container')]");
    private final ElementsCollection closeTabIconsLocator = $$x("//ul[contains(@class,'nav-tabs')]/li//div[contains(@class,'tab-bar')]//span[contains(@class,'close-tab-icon')]");
    private final SelenideElement patientInfoFrameLoc = $x("//*[@name='frmPatientInfo']");
    private final SelenideElement patientInfo1FrameLoc = $x("//*[@id='frmPatientInfo1']");
    private final SelenideElement searchPatientInput = $x("//mat-form-field[contains(@class,'touched')]//input[@data-placeholder='Search for patient']");
    private final SelenideElement memosLeftPanelItem = $x("//amds-left-panel-item[contains(@class,'ng-tns') and .//span[contains(text(),'emos')]]");
    private final SelenideElement notesLeftPanelItem = $x("//amds-left-panel-item[contains(@class,'ng-tns') and .//span[contains(text(),'Note')]]");
    private final SelenideElement chartFilesLeftPanelItem = $x("//amds-left-panel-item[contains(@class,'ng-tns') and .//span[contains(text(),'hart Files')]]");

    public static void switchToFrame(SelenideElement locator) {
        switchTo().defaultContent().switchTo().frame(locator);
    }

    public boolean searchAndOpenPatientWithRetry(String name, int maxRetries) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                return searchAndOpenPatient(name);
            } catch (Exception e) {
                log.error("Error in searchAndOpenPatient attempt {}: {}", attempt, e.getMessage());
                refresh();
                waitForDomToLoad();
                sleep(2000);
            }
        }
        return false;
    }

    private boolean searchAndOpenPatient(String name) {
        switchToFrame(patientInfoFrameLoc);
        waitForDomToLoad();
        awaitSafe(Duration.ofSeconds(4), Duration.ofMillis(750), () -> searchPatientInput, Matchers.is(exist));
        searchPatientInput.sendKeys(Keys.COMMAND, "A");
        searchPatientInput.sendKeys(Keys.BACK_SPACE);
        searchPatientInput.sendKeys(name);
        awaitSafe(Duration.ofSeconds(4), Duration.ofMillis(750), firstLookupElement::first, Matchers.is(visible));
        if (!firstLookupElement.isEmpty()) {
            firstLookupElement.first().click();
            return true;
        } else {
            log.info("No search results found for: {} ", name);
            return false;
        }
    }

    public HomePageAdvancedMd openPatientTab() {
        switchTo().defaultContent();
        awaitSafe(Duration.ofSeconds(2), Duration.ofMillis(500), () -> patientInfoBtn, Matchers.is(visible));
        patientInfoBtn.shouldBe(enabled).click();
        return this;
    }

    public HomePageAdvancedMd waitUntilHomePageIsOpened() {
        for (int i = 0; i < 5; i++) {
            var handles = getWebDriver().getWindowHandles();
            if (handles.size() < 2) {
                Selenide.sleep(3000);
            }
            waitForDomToLoad();
        }
        return this;
    }

    public PatientPageAdvancedMd openMemosTab() {
        switchToFrame(patientInfo1FrameLoc);
        memosLeftPanelItem.shouldBe(enabled).shouldBe(visible).click();
        return new PatientPageAdvancedMd().isMemosTabOpened();
    }

    public PatientPageAdvancedMd openNotesTab() {
        try {
            switchToFrame(patientInfo1FrameLoc);
            notesLeftPanelItem.shouldBe(enabled).shouldBe(visible).click();
            return new PatientPageAdvancedMd().isNotesTabOpened();
        } catch (UnhandledAlertException e) {
            handleAlert(e);
            return openNotesTab();
        }
    }

    private void handleAlert(UnhandledAlertException exception) {
        Alert alert = getWebDriver().switchTo().alert();
        log.info("Alert Text: " + alert.getText());
        log.info(exception.toString());
        alert.dismiss();
    }

    public PatientPageAdvancedMd openChartFilesTab() {
        try {
            switchToFrame(patientInfo1FrameLoc);
            chartFilesLeftPanelItem.scrollIntoView(false).shouldBe(visible).click();
            return new PatientPageAdvancedMd().isChartFilesTabOpened();
        } catch (UnhandledAlertException e) {
            handleAlert(e);
            return openChartFilesTab();
        }
    }

    public void closeAllTabs() {
        switchTo().defaultContent();
        sleep(700);
        if (closeTabIconsLocator.size() >= 2) {
            closeTabIconsLocator.get(1).shouldBe(enabled).click();
        }
    }
}
