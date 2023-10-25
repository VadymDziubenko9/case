package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static utils.ConfirmationsUtil.closeAllBubbles;
import static utils.ConfirmationsUtil.waitTillBubbleMessageShown;

@Slf4j
public class CaseDuplicationPage {

    private final SelenideElement listOfValues = $x("//ul[contains(@role,'listbox')]");
    private final SelenideElement optionsLocators = $x("//div[contains(@class, 'MuiFormControl-root') and label[text()='Options']]");
    private final SelenideElement teamLocator = $x("//div[contains(@class, 'MuiFormControl-root') and label[text()='Team']]");
    private final SelenideElement mediaLocators = $x("//div[contains(@class, 'MuiFormControl-root') and label[text()='Media']]");
    private final SelenideElement documentLocators = $x("//div[contains(@class, 'MuiFormControl-root') and label[text()='Documents']]");
    private final SelenideElement createCaseDuplicateButton = $x("//button[@data-action-button='createCaseDuplicateDialog']");
    private final SelenideElement caseDuplicateDialog = $x("//form[contains(@role,'dialog') and .//text()='Create case duplicate']");

    private static final String OPEN_BTN = ".//button[contains(@title,'Open')]";
    private static final String CLOSE_BTN = ".//button[contains(@title,'Close')]";

    private void selectElements(List<String> documents, SelenideElement element) {
        IntStream.range(0, documents.size()).boxed().forEach(index -> {
            element.$x(OPEN_BTN).shouldBe(enabled).click();
            listOfValues.should(exist);
            listOfValues.$x(".//li[contains(normalize-space(),'%s')]".formatted(documents.get(index))).should(exist).scrollTo().click();
            element.$x(".//div[contains(@class,'MuiButtonBase-root') and normalize-space()='%s']".formatted(documents.get(index))).shouldBe(visible);
        });
    }

    public CaseDuplicationPage setDocuments(List<String> elements) {
        log.info("Setting elements for case duplication: {}", elements);
        selectElements(elements, documentLocators);
        return this;
    }

    public CaseDuplicationPage setMedia(List<String> elements) {
        log.info("Setting media for case duplication: {}", elements);
        selectElements(elements, mediaLocators);
        return this;
    }

    public CaseDuplicationPage setOptions(List<String> elements) {
        log.info("Setting options for case duplication: {}", elements);
        selectElements(elements, optionsLocators);
        return this;
    }

    public CaseDuplicationPage setTeam(String team) {
        log.info("Setting team for case duplication: {}", team);
        teamLocator.$x(OPEN_BTN).shouldBe(enabled).click();
        listOfValues.should(exist);
        listOfValues.$x(".//li[contains(normalize-space(),'%s')]".formatted(team)).should(enabled).scrollTo().click();
        teamLocator.$x(".//input[@value='%s']".formatted(team)).shouldBe(visible);
        return this;
    }

    private List<String> getDropDownElements(SelenideElement locator) {
        locator.$x(OPEN_BTN).shouldBe(visible).click();
        var result = listOfValues.$$x(".//li").texts();
        locator.$x(CLOSE_BTN).shouldBe(visible).click();
        return result;
    }

    public List<String> getDropDownOptions() {
        return getDropDownElements(optionsLocators);
    }

    public List<String> getDropDownMedia() {
        return getDropDownElements(mediaLocators);
    }

    public void submitCaseDuplicate() {
        log.info("Submit case duplication");
        createCaseDuplicateButton.shouldBe(enabled).click();
        caseDuplicateDialog.should(disappear).shouldNotBe(visible);
        waitTillBubbleMessageShown("Case duplicate was created");
        closeAllBubbles();
        sleep(Duration.ofSeconds(5).toMillis()); //case document does not exist if there is no wait
    }


    public static List<String> getExpectedMediaList() {
        return List.of("Pizigani_1367_Chart_10MB.jpg.zip");
    }
}
