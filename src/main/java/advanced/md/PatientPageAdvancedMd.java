package advanced.md;

import advanced.md.dto.Patient;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.NonNull;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.*;

import static advanced.md.HomePageAdvancedMd.switchToFrame;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static utils.JsUtil.waitForDomToLoad;

public class PatientPageAdvancedMd {
    private final SelenideElement patientDataFrame = $x("//div[contains(@class,'main-container')]//*[@name='Patient']");
    private final SelenideElement patientNameLocator = $x("//div[@id='ellPtPatient']");
    private final SelenideElement responsibilityPartyNameLocator = $x("//div[@id='ellPtRespParty']");

    private final SelenideElement emailLocator = $x("//input[@id='txtPtEmail']");
    private final SelenideElement notesLocator = $x("//textarea[@id='txtjQCommunicationNote']");

    private final SelenideElement financialClassLocator = $x("//input[@id='txtPtFinClass']");
    private final SelenideElement financialClassDescriptionLocator = $x("//div[@id='ellPtFinClass']");

    private final SelenideElement providerProfileLocator = $x("//input[@id='txtPtProvCode']");
    private final SelenideElement providerProfileDescriptionLocator = $x("//div[@id='ellPtProfile']");

    private final SelenideElement preferredLanguageLocator = $x("//div[@id='ellLanguage']");
    private final SelenideElement memosDataContainer = $x("//div[contains(@id,'divOuter') and .//span[contains(@id,'spnTabName') and text()='Memos - ']]");
    private final SelenideElement notesDataContainer = $x("//div[contains(@id,'divOuter') and .//span[contains(@id,'spnTabName') and text()='Patient Notes - ']]");
    private final SelenideElement chartFilesDataContainer = $x("//div[contains(@id,'divOuter') and .//span[contains(@id,'spnTabName') and text()='Chart Files - ']]");
    private final ElementsCollection memosTables = $$x("//table[@id='tblMemos']//tr");
    private final ElementsCollection notesTables = $$x("//div[@id='divNoteGrid']//table//tr");
    private final SelenideElement notesAndMemosFrameLocator = $x("//div[contains(@class,'iframe-container')]//*[@name='Patient']");
    private final SelenideElement patientInfo1FrameLoc = $x("//div[@id='frmPatientInfo1Parent']//*[@name='frmPatientInfo']");
    private final SelenideElement patientInfoFrameLoc = $x("//*[@name='frmPatientInfo']");

    private final SelenideElement chartNumber = $x("//div[@class='chart-item']");
    private final SelenideElement preferredPhone = $x("//input[@id='txtPtPreferredPhone']");
    private final ElementsCollection fullAddress = $$x("//div[contains(@fxlayout,'row') and @fxlayoutalign='start start']/div/div[4]/span");
    private final ElementsCollection dateAndSexLocator = $$x("//div[contains(@fxlayout,'row wrap') and @fxlayoutalign='start start'][1]/span");


    public void switchToPatientInfoFrame() {
        int currentTabs = getNumberOfOpenTabs();
        if (currentTabs >= 3) {
            closeLastTab();
            switchToFrame(patientInfoFrameLoc);
        }
        waitForDomToLoad();
        switchTo().frame(patientDataFrame);
        sleep(1500);
    }

    private int getNumberOfOpenTabs() {
        return WebDriverRunner.getWebDriver().getWindowHandles().size();
    }

    private void closeLastTab() {
        Set<String> windowHandles = WebDriverRunner.getWebDriver().getWindowHandles();
        List<String> handlesList = new ArrayList<>(windowHandles);

        if (handlesList.size() > 1) {
            String lastHandle = handlesList.get(handlesList.size() - 1);
            String mainWindowHandle = handlesList.get(handlesList.size() - 2);
            WebDriverRunner.getWebDriver().switchTo().window(lastHandle);
            WebDriverRunner.getWebDriver().close();
            WebDriverRunner.getWebDriver().switchTo().window(mainWindowHandle);
        }
    }

    public Patient parsePatientData() {
        var getChartNumber = Arrays.stream(chartNumber.getOwnText().split("-")).toList().get(0).trim();
        var getAddress = fullAddress.get(0).getOwnText().isEmpty() ? null : fullAddress.get(0).getOwnText();
        var getAptSuite = fullAddress.get(1).getOwnText().isEmpty() ? null : fullAddress.get(1).getOwnText();
        var getCity = fullAddress.get(2).getOwnText().isEmpty() ? null : fullAddress.get(2).getOwnText();
        var getState = fullAddress.get(3).getOwnText().isEmpty() ? null : fullAddress.get(3).getOwnText();
        var getZipCode = fullAddress.get(4).getOwnText().isEmpty() ? null : fullAddress.get(4).getOwnText();
        var getDateOfBirth = dateAndSexLocator.get(0).getOwnText().isEmpty() ? null : dateAndSexLocator.get(0).getOwnText();
        var getPatientYears = dateAndSexLocator.get(1).getOwnText().isEmpty() ? null : dateAndSexLocator.get(1).getOwnText();
        var getPatientSex = dateAndSexLocator.get(2).getOwnText().isEmpty() ? null : dateAndSexLocator.get(2).getOwnText();
        switchToPatientInfoFrame();
        return Patient.builder()
                .address(getAddress)
                .zipCode(getZipCode)
                .city(getCity)
                .state(getState)
                .chartNumber(getChartNumber)
                .aptSuite(getAptSuite)
                .notes(notesLocator.getText().isEmpty() ? null : notesLocator.getText())
                .dateOfBirth(getDateOfBirth + " , " + getPatientYears)
                .sex(getPatientSex)
                .patientName(getValueByAttribute(patientNameLocator))
                .responsiblePartyName(getValueByAttribute(responsibilityPartyNameLocator))
                .preferredLanguage(getValueByAttribute(preferredLanguageLocator))
                .email(getTextFromClipboard(emailLocator))
                .preferredPhone(getTextFromClipboard(preferredPhone))
                .financialClass(getValueByAttribute(financialClassDescriptionLocator) + " , " + getTextFromClipboard(financialClassLocator))
                .providerProfile(getValueByAttribute(providerProfileDescriptionLocator) + " , " + getTextFromClipboard(providerProfileLocator))
                .build();
    }

    public String getValueByAttribute(SelenideElement locator) {
        return Objects.requireNonNull(locator.getAttribute("data-returnvalue")).isEmpty()
                ? null
                : locator.getAttribute("data-returnvalue");
    }


    private String getTextFromClipboard(@NonNull SelenideElement locator) {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
            locator.click();
            locator.sendKeys(Keys.COMMAND + "A");
            locator.sendKeys(Keys.COMMAND + "C");
            String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            if (data.isEmpty()) {
                return null;
            } else {
                return data;
            }
        } catch (UnsupportedFlavorException | IOException e) {
            throw new ClipboardOperationException("Error while performing clipboard operation", e);
        }
    }

    public PatientPageAdvancedMd isMemosTabOpened() {
        switchTo().defaultContent();
        switchTo().frame(patientInfo1FrameLoc);
        switchTo().frame(notesAndMemosFrameLocator);
        memosDataContainer.shouldBe(exist);
        return this;
    }

    public PatientPageAdvancedMd isNotesTabOpened() {
        switchTo().defaultContent();
        switchTo().frame(patientInfo1FrameLoc);
        switchTo().frame(notesAndMemosFrameLocator);
        notesDataContainer.shouldBe(exist);
        return this;
    }

    public PatientPageAdvancedMd isChartFilesTabOpened() {
        switchTo().defaultContent();
        switchTo().frame(patientInfo1FrameLoc);
        switchTo().frame(notesAndMemosFrameLocator);
        chartFilesDataContainer.shouldBe(exist);
        return this;
    }

    public List<String> parsePatientMemos() {
        List<String> parsedMemos = new ArrayList<>();
        String text;
        if (!memosTables.get(0).$x("./td[1]").getText().isEmpty()) {
            for (int i = 0; i < memosTables.size(); i++) {
                text = memosTables.asFixedIterable()
                        .stream()
                        .toList()
                        .get(i)
                        .$x(".//td[2]")
                        .getText();
                parsedMemos.add(i, text);
            }
            return parsedMemos;
        }
        return Collections.emptyList();
    }

    public List<String> parsePatientNotes() {
        List<String> parsedNotes = new ArrayList<>();
        if (!notesTables.get(0).$x("./td[1]").getText().trim().isEmpty()) {
            for (SelenideElement notesTable : notesTables) {
                List<String> parsedNotesRow = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    parsedNotesRow.add(notesTable.$x("./td[%s]".formatted(i)).getText());
                }
                parsedNotes.add(parsedNotesRow.toString());
            }
            return parsedNotes;
        }
        return Collections.emptyList();
    }

    public List<String> getChartFilesId() {
        var locator = $$x("//div[@id='divChartFileList']//tr");
        List<String> files = new ArrayList<>();
        if (locator.get(0).isDisplayed() && !Objects.requireNonNull(locator.get(0).getAttribute("id")).isEmpty()) {
            for (SelenideElement selenideElement : locator) {
                files.add(Objects.requireNonNull(selenideElement.getAttribute("id")).replace("file", ""));
            }
            return files;
        }
        return Collections.emptyList();
    }

    public static class ClipboardOperationException extends RuntimeException {
        public ClipboardOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
