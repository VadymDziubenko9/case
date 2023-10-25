package page.object.portal.cases;

import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import page.object.portal.models.Episode;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;
import static utils.ConfirmationsUtil.closeAllBubbles;
import static utils.ConfirmationsUtil.waitTillBubbleMessagesShown;

@AllArgsConstructor
@Slf4j
public class ModalPage {

    private static final String CONTROL_INPUT = "//div[@data-control-input='%s']//input";
    private final SelenideElement episodeAuthorInput = $x(CONTROL_INPUT.formatted("author"));
    private final SelenideElement episodeTypeInput = $x(CONTROL_INPUT.formatted("type"));
    private final SelenideElement episodeDateInput = $x(CONTROL_INPUT.formatted("date"));
    private final SelenideElement episodeTimeInput = $x(CONTROL_INPUT.formatted("time"));

    private final SelenideElement newEpisodeForm = $x("//div[contains(@role,'dialog') and .//h2[contains(normalize-space(),'Create')]]");
    private final SelenideElement existingEpisodeForm = $x("//div[contains(@data-test,'episodeListItem')]//form");
    private final SelenideElement openedModalViewLoc = $x("//h2[contains(@class,'MuiTypography-root') and .//h6[contains(text(),'.pdf')]]");
    private final SelenideElement createEpisodeButton = $x("//button[@data-action-button='createEpisode']");
    private final SelenideElement closeModalViewLoc = $x("//h2[contains(@class,'MuiTypography-root')]//button[@aria-label='Close dialog button']");
    private final SelenideElement createEpisodeBtn = $x("//button[@data-action-button='createEpisodeFormDialog']");
    private final SelenideElement saveEpisodeOnEditButton = $x("//button[@data-action-button='saveEpisodeForm']");
    private final SelenideElement modalViewContainerLoc = $x("//div[contains(@class,'MuiDialogContent-root')]");

    public ModalPage verifyIfPageOpened(){
        modalViewContainerLoc.shouldBe(exist).shouldBe(visible);
        return this;
    }

    public void closeModalView(){
        if(closeModalViewLoc.isDisplayed()){
            closeModalViewLoc.shouldBe(enabled).click();
        }
    }

    public void refreshPage(){
        refresh();
    }

    public ModalPage openCreateEpisodeForm(){
        modalViewContainerLoc.shouldBe(visible);
        createEpisodeButton.shouldBe(enabled).click();
        newEpisodeForm.should(appear).shouldBe(visible);
        return this;
    }

    public ModalPage fillInEpisodeForm() {
        var episode = Episode.builder().build();
        episodeAuthorInput.shouldBe(exist).sendKeys(episode.getAuthor());
        episodeTypeInput.shouldBe(exist).sendKeys(episode.getType());
        episodeDateInput.shouldBe(exist).sendKeys(episode.getDate());
        if(episode.getDate() != null){
            episodeAuthorInput.shouldBe(exist).sendKeys(episode.getAuthor());
        }
        return this;
    }

    public void submit(){
        createEpisodeBtn.shouldBe(enabled).click();
        waitTillBubbleMessagesShown("Episode was created", "Episode was updated");
        closeAllBubbles();
    }

    public List<Episode> parseEpisodes(){
        var author = existingEpisodeForm.$x(CONTROL_INPUT.formatted("author")).getAttribute("value");
        var type = existingEpisodeForm.$x(CONTROL_INPUT.formatted("type")).getAttribute("value");
        var date = existingEpisodeForm.$x(CONTROL_INPUT.formatted("date")).getAttribute("value");
        var time = existingEpisodeForm.$x(CONTROL_INPUT.formatted("time")).getAttribute("value");
        return List.of(Episode.builder().author(author).type(type).date(date).time(time).build());
    }
}
