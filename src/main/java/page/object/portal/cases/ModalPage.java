package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import page.object.portal.models.Episode;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utils.ConfirmUtil.*;

@AllArgsConstructor
@Slf4j
public class ModalPage {

    private static final String CONTROL_INPUT = "//div[@data-control-input='%s']//input";
    private static final String ACTION_BUTTON = "//button[@data-action-button='%s']";

    private final SelenideElement episodeAuthorInput = $x(CONTROL_INPUT.formatted("author"));
    private final SelenideElement episodeTypeInput = $x(CONTROL_INPUT.formatted("input"));
    private final SelenideElement episodeDateInput = $x(CONTROL_INPUT.formatted("date"));
    private final SelenideElement episodeTimeInput = $x(CONTROL_INPUT.formatted("time"));
    private final SelenideElement episodeNotesInput = $x("//div[contains(@class,'ql-editor') and @data-placeholder='Notes']");
    private final SelenideElement markEpisodeAsNotParentBtn = $x(ACTION_BUTTON.formatted("markEpisodeAsNotParent)"));
    private final SelenideElement markEpisodeAsParentBtn = $x(ACTION_BUTTON.formatted("markEpisodeAsParent)"));
    private final SelenideElement deleteEpisodeBtn = $x(ACTION_BUTTON.formatted("deleteEpisode"));
    private final SelenideElement selectedPreEventCheckbox = $x("//label[contains(@data-control-checkbox,'preEvent') and .//span[contains(@class,'Mui-checked')]]");
    private final SelenideElement newEpisodeForm = $x("//div[contains(@role,'dialog') and .//h2[contains(normalize-space(),'Create')]]");
    private final SelenideElement existingEpisodeForm = $x("//div[contains(@data-test,'episodeListItem')]//form");
    private final SelenideElement openedModalViewLoc = $x("//h2[contains(@class,'MuiTypography-root') and .//h6[contains(text(),'.pdf')]]");
    private final SelenideElement createEpisodeButton = $x("//button[@data-action-button='createEpisode']");
    private final SelenideElement closeModalViewLoc = $x("//h2[contains(@class,'MuiTypography-root')]//button[@aria-label='Close dialog button']");
    private final SelenideElement createEpisodeBtn = $x("//button[@data-action-button='createEpisodeFormDialog']");
    private final SelenideElement saveEpisodeBtn = $x("//button[@data-action-button='saveEpisodeForm']");
    private final SelenideElement saveEpisodeOnEditButton = $x("//button[@data-action-button='saveEpisodeForm']");
    private final SelenideElement modalViewContainerLoc = $x("//div[contains(@class,'MuiDialogContent-root')]");

    public ModalPage verifyIfPageOpened() {
        modalViewContainerLoc.shouldBe(exist).shouldBe(visible);
        return this;
    }

    public ModalPage closeModalView() {
        if (closeModalViewLoc.isDisplayed()) {
            closeModalViewLoc.shouldBe(enabled).click();
        }
        return this;
    }

    public void refreshPage() {
        refresh();
    }

    public ModalPage openCreateEpisodeForm() {
        modalViewContainerLoc.shouldBe(visible);
        createEpisodeButton.shouldBe(enabled).click();
        newEpisodeForm.should(appear).shouldBe(visible);
        return this;
    }

    public ModalPage fillInEpisodeForm(Episode episode) {
        episodeAuthor(episode);
        episodeType(episode);
        episodeDate(episode);
        if (episode.getDate() != null) {
            episodeTime(episode);
        }
        return this;
    }

    private void episodeTime(@NonNull Episode episode) {
        clearInput(episodeTimeInput);
        episodeTimeInput.shouldBe(exist).sendKeys(episode.getTime());
    }

    private void episodeDate(@NonNull Episode episode) {
        clearInput(episodeDateInput);
        episodeDateInput.shouldBe(exist).sendKeys(episode.getDate());
    }

    private void episodeType(@NonNull Episode episode) {
        clearInput(episodeTypeInput);
        episodeTypeInput.shouldBe(exist).sendKeys(episode.getType());
    }

    private void episodeAuthor(@NonNull Episode episode) {
        clearInput(episodeAuthorInput);
        episodeAuthorInput.shouldBe(exist).sendKeys(episode.getAuthor());
    }

    public ModalPage episodeNotes(Episode episode){
        clearNotesInput();
        episodeNotesInput.shouldBe(enabled).sendKeys(episode.getNotes());
        return this;
    }

    private void clearNotesInput(){
        while (!episodeNotesInput.$x(".//p").getText().isEmpty()){
            actions().sendKeys(episodeNotesInput, Keys.BACK_SPACE).build().perform();
        }

    }
    public ModalPage submit() {
        createEpisodeBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown("Episode was created", "Episode was updated");
        closeAllBubbles();
        return this;
    }

    public ModalPage saveEpisodeOnEdit() {
        saveEpisodeBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown("Episode was created", "Episode was updated");
        closeAllBubbles();
        return this;
    }

    public Episode parseEpisode() {
        var attribute = "value";
        return Episode.builder()
                .author(episodeAuthorInput.getAttribute(attribute))
                .type(episodeTypeInput.getAttribute(attribute))
                .date(episodeDateInput.getAttribute(attribute))
                .time(episodeTimeInput.getAttribute(attribute))
                .notes(episodeNotesInput.getText())
                .build();
    }

    public void deleteEpisode() {
        deleteEpisodeBtn.shouldBe(enabled).click();
        waitTillBubbleMessageShown("Episode was deleted");
        closeAllBubbles();
    }

    public boolean isEpisodeMarkedAsParent() {
        return !markEpisodeAsParentBtn.isDisplayed();
    }

    public boolean isPreEventToggleSelected() {
        return selectedPreEventCheckbox.isDisplayed();
    }

}
