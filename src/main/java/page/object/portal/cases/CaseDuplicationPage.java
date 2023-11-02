package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static utils.ConfirmUtil.*;
import static utils.ConfirmUtil.waitTillBubbleMessageShown;

@Slf4j
public class CaseDuplicationPage {
    private static final String FORM_CONTROL_LABEL = "//div[contains(@class, 'MuiFormControl-root') and label[text()='%s']]";
    private static final String OPEN_BTN = ".//button[contains(@title,'Open')]";
    private static final String CLOSE_BTN = ".//button[contains(@title,'Close')]";

    private final SelenideElement listOfValues = $x("//ul[contains(@role,'listbox')]");
    private final SelenideElement optionsLocators = $x(FORM_CONTROL_LABEL.formatted("Options"));
    private final SelenideElement teamLocator = $x(FORM_CONTROL_LABEL.formatted("Team"));
    private final SelenideElement mediaLocators = $x(FORM_CONTROL_LABEL.formatted("Media"));
    private final SelenideElement documentLocators = $x(FORM_CONTROL_LABEL.formatted("Documents"));
    private final SelenideElement createCaseDuplicateButton = $x("//button[@data-action-button='createCaseDuplicateDialog']");
    private final SelenideElement caseDuplicateDialog = $x("//form[contains(@role,'dialog') and .//text()='Create case duplicate']");


    private void selectElements(@NonNull List<String> documents, SelenideElement element) {
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

    private @NonNull List<String> getDropDownElements(@NonNull SelenideElement locator) {
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
