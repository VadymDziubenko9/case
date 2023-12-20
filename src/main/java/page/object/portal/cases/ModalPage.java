package page.object.portal.cases;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.Episode;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static utils.JsUtil.waitForDomToLoad;
import static utils.WebDriverUtil.getOperatingSystem;

@AllArgsConstructor
@Slf4j
public class ModalPage extends BaseAbstractPage{
    private static final String CONTROL_INPUT = "//div[@data-control-input='%s']//input";
    private static final String ACTION_BUTTON = "//button[@data-action-button='%s']";
    private static final String EPISODE_CONTENT_DATE = "//div[contains(@class,'MuiBox-root') and ./span[contains(@class,'episode-content-date') and text()='%s' and text()=' %s'] and ./span[contains(@class,'episode-content-info') and text()='%s' and text()='%s']]";

    private static final String EPISODE_IS_DELETED = "Episode is deleted";
    private static final String EPISODE_IS_CREATED = "Episode is created";
    private static final String EPISODE_IS_UPDATED = "Episode is updated";

    private final SelenideElement episodeAuthorInput = $x(CONTROL_INPUT.formatted("author"));
    private final SelenideElement episodeTypeInput = $x(CONTROL_INPUT.formatted("input"));
    private final SelenideElement episodeDateInput = $x(CONTROL_INPUT.formatted("date"));
    private final SelenideElement episodeTimeInput = $x(CONTROL_INPUT.formatted("time"));
    private final SelenideElement episodeNotesInput = $x("//div[contains(@class,'ql-editor') and @data-placeholder='Notes']");
    private final SelenideElement deleteEpisodeBtn = $x(ACTION_BUTTON.formatted("deleteEpisode"));
    private final SelenideElement selectedPreEventCheckbox = $x("//label[contains(@data-control-checkbox,'preEvent') and .//span[contains(@class,'Mui-checked')]]");
    private final SelenideElement newEpisodeForm = $x("//div[contains(@role,'dialog') and .//h2[contains(normalize-space(),'Create')]]");
    private final SelenideElement createEpisodeButton = $x("//button[@data-action-button='createEpisode']");
    private final SelenideElement closeModalViewLoc = $x("//h2[contains(@class,'MuiTypography-root')]//button[@aria-label='Close dialog button']");
    private final SelenideElement createEpisodeBtn = $x("//button[@data-action-button='createEpisodeFormDialog']");
    private final SelenideElement saveEpisodeBtn = $x("//button[@data-action-button='saveEpisodeForm']");
    private final SelenideElement modalViewContainerLoc = $x("//div[contains(@class,'MuiDialogContent-root')]");
    private final SelenideElement episodeListItemLoc = $x("//div[contains(@data-test,'episodeListItem')]");

    private final ElementsCollection suggestedOptions = $$x("//ul/li[contains(@class,'MuiAutocomplete-option')]");

    public ModalPage verifyIfPageOpened() {
        modalViewContainerLoc.shouldBe(exist).shouldBe(visible);
        return this;
    }

    public void closeModalView() {
        if (closeModalViewLoc.isDisplayed()) {
            closeModalViewLoc.shouldBe(enabled).click();
        }
    }

    public List<String> getListOfSuggestedAuthors() {
        return suggestedOptions.shouldHave(CollectionCondition.sizeGreaterThan(0))
                .asFixedIterable()
                .stream()
                .map(SelenideElement::getText)
                .toList();
    }

    public boolean verifyIsSuggestedAuthorMarkedAsVerified(String author) {
        var selenideElements = suggestedOptions.asFixedIterable().stream().toList();
        for (SelenideElement element : selenideElements) {
            if (element.$x(".//*[@data-testid='CheckCircleIcon']/..").isDisplayed() && (element.getText().contains(author))) {
                return true;
            }
        }
        return false;
    }

    @Step("Open create episode form")
    public ModalPage openCreateEpisodeForm() {
        log.info("Opening episode creation form");
        modalViewContainerLoc.shouldBe(visible);
        createEpisodeButton.shouldBe(enabled).click();
        newEpisodeForm.should(appear).shouldBe(visible);
        return this;
    }

    public ModalPage fillInEpisodeForm(Episode episode) {
        fillEpisodeAuthor(episode);
        fillEpisodeType(episode);
        fillEpisodeDate(episode);
        if (episode.getDate() != null) {
            fillEpisodeTime(episode);
        }
        return this;
    }

    @Step("Fill in episode time field")
    private void fillEpisodeTime(Episode episode) {
        log.info("Filling in an episode time field");
        clearInput(episodeTimeInput);
        episodeTimeInput.shouldBe(exist).sendKeys(episode.getTime());
    }

    @Step("Fill in episode date field")
    private void fillEpisodeDate(@NonNull Episode episode) {
        log.info("Filling in an episode date field");
        clearInput(episodeDateInput);
        episodeDateInput.shouldBe(exist).sendKeys(episode.getDate());
    }

    @Step("Fill in episode type field")
    private void fillEpisodeType(@NonNull Episode episode) {
        log.info("Filling in an episode type field");
        clearInput(episodeTypeInput);
        episodeTypeInput.shouldBe(exist).sendKeys(episode.getType());
    }

    @Step("Fill in episode author field")
    public void fillEpisodeAuthor(@NonNull Episode episode) {
        log.info("Filling in an episode author field");
        clearInput(episodeAuthorInput);
        episodeAuthorInput.shouldBe(exist).sendKeys(episode.getAuthor());
    }

    @Step("Fill in episode notes")
    public ModalPage fillEpisodeNotes(@NonNull Episode episode) {
        log.info("Filling in an episode notes");
        clearNotesInput();
        episodeNotesInput.shouldBe(enabled).sendKeys(episode.getNotes());
        return this;
    }

    @Step("Clear episode notes")
    private void clearNotesInput() {
        log.info("Clearing an episode notes");
        if (getOperatingSystem().toLowerCase().contains("mac os")) {
            episodeNotesInput.sendKeys(Keys.COMMAND, "A");
        } else {
            episodeNotesInput.sendKeys(Keys.CONTROL, "A");
        }
        episodeNotesInput.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Save episode action")
    public void saveEpisode() {
        log.info("Saving episode");
        createEpisodeBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown(EPISODE_IS_CREATED, EPISODE_IS_UPDATED);
        closeAllBubbles();
    }

    public void saveEpisodeOnEdit() {
        saveEpisodeBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown(EPISODE_IS_CREATED, EPISODE_IS_UPDATED);
        closeAllBubbles();
    }

    @Step("Parse episode action")
    public Episode parseEpisode() {
        log.info("Parsing episode");
        var attribute = "value";
        return Episode.builder()
                .author(Objects.requireNonNull(episodeAuthorInput.getAttribute(attribute))
                        .isEmpty() ? null : episodeAuthorInput.getAttribute(attribute))
                .type(Objects.requireNonNull(episodeTypeInput.getAttribute(attribute))
                        .isEmpty() ? null : episodeTypeInput.getAttribute(attribute))
                .date(Objects.requireNonNull(episodeDateInput.getAttribute(attribute))
                        .isEmpty() ? null : episodeDateInput.getAttribute(attribute))
                .time(Objects.requireNonNull(episodeTimeInput.getAttribute(attribute))
                        .isEmpty() ? null : episodeTimeInput.getAttribute(attribute))
                .notes(episodeNotesInput.getText().isEmpty() ? null : episodeNotesInput.getText())
                .build();
    }

    @Step("Delete episode action")
    public void deleteAllEpisodes() {
        while (episodeListItemLoc.isDisplayed()) {
            deleteEpisodeBtn.shouldBe(enabled).click();
            waitTillBubbleMessageShown(EPISODE_IS_DELETED);
            closeAllBubbles();
        }
    }

    public void ifNotParentMarkAsParent(@NonNull Episode episode) {
        var element = $x(EPISODE_CONTENT_DATE.formatted(episode.getDate(), episode.getTime(), episode.getAuthor(), episode.getType()) + "//.." + ACTION_BUTTON.formatted("markAsAParentEpisode"));
        if (element.isDisplayed()) {
            element.click();
        }
    }

    public boolean isPreEventToggleSelected() {
        return selectedPreEventCheckbox.isDisplayed();
    }

    public boolean isIncludePageIntoWorkspaceSelected() {
        waitForDomToLoad();
        return modalViewContainerLoc.$x(".//span[contains(@class,'Mui-checked') and .//input[@id='inWorkspace']]").is(visible);
    }

    public ModalPage tryToFindAuthorByKeyWords(String authorName) {
        episodeAuthorInput.sendKeys(authorName);
        return this;
    }

}
