package widgets;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

@Slf4j
@UtilityClass
public class ListboxFilterWidget {
    private final SelenideElement workspaceFilterEpisodeType = $x("//div[contains(@class,'MuiFormControl-root') and .//@id='workspaceFilterEpisodeType-label']//input");
    private final SelenideElement workspaceFilterEpisodeAuthor = $x("//div[contains(@class,'MuiFormControl-root') and .//@id='workspaceFilterEpisodeAuthor-label']//input");
    private final SelenideElement workspaceFilterEpisodeTypeListBox = $x("//ul[contains(@id,'workspaceFilterEpisodeType-listbox')]");
    private final SelenideElement workspaceFilterEpisodeAuthorListBox = $x("//ul[contains(@id,'workspaceFilterEpisodeAuthor-listbox')]");
    private final SelenideElement closeDropDownListBoxBtn = $x("//button[@title='Close']");
    private final String LIST_ELEMENT = ".//li[contains(text(),'%s')]";

    @Step("Filtering by {} types")
    public void filteringByEpisodeTypes(String @NonNull ... types) {
        log.info("User is filtering workspace pages by {} types", Arrays.stream(types).toList());
        for (String type : types) {
            workspaceFilterEpisodeType.shouldBe(visible).click();
            workspaceFilterEpisodeTypeListBox.shouldBe(visible).$x(LIST_ELEMENT.formatted(type)).shouldBe(visible).click();
        }
    }

    @Step("Filtering by {} authors")
    public void filteringByEpisodeAuthors(String @NonNull ... authors) {
        log.info("User is filtering workspace pages by {} authors", Arrays.stream(authors).toList());
        for (String author : authors) {
            workspaceFilterEpisodeAuthor.shouldBe(visible).click();
            workspaceFilterEpisodeAuthorListBox.shouldBe(visible).$x(LIST_ELEMENT.formatted(author)).shouldBe(visible).click();
        }
    }

    public boolean verifyWhetherSelectedTypeIsNotAvailableForTheSecondTime(String type) {
        workspaceFilterEpisodeType.shouldBe(visible).click();
        if (workspaceFilterEpisodeTypeListBox.shouldBe(visible).$x(LIST_ELEMENT.formatted(type)).isDisplayed()) {
            closeDropDownListBoxBtn.shouldBe(enabled).click();
            return false;
        }
        closeDropDownListBoxBtn.shouldBe(enabled).click();
        return true;
    }

    public boolean verifyWhetherSelectedAuthorIsNotAvailableForTheSecondTime(String author) {
        workspaceFilterEpisodeAuthor.shouldBe(visible).click();
        if (workspaceFilterEpisodeAuthorListBox.shouldBe(visible).$x(LIST_ELEMENT.formatted(author)).isDisplayed()) {
            closeDropDownListBoxBtn.shouldBe(enabled).click();
            return false;
        }
        closeDropDownListBoxBtn.shouldBe(enabled).click();
        return true;
    }
}
