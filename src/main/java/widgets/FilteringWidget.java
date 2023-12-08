package widgets;

import com.codeborne.selenide.SelenideElement;
import org.testng.Assert;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class FilteringWidget {
    private static final SelenideElement workspaceFilterEpisodeType = $x("//div[contains(@class,'MuiFormControl-root') and .//@id='workspaceFilterEpisodeType-label']//input");
    private static final SelenideElement workspaceFilterEpisodeLabel = $x("//div[contains(@class,'MuiFormControl-root') and .//@id='workspaceFilterEpisodeLabel-label']//input");
    private static final SelenideElement workspaceFilterEpisodeAuthors = $x("//div[contains(@class,'MuiFormControl-root') and .//@id='workspaceFilterEpisodeAuthor-label']//input");
    private static final SelenideElement workspaceFilterEpisodeTypeListBox = $x("//ul[contains(@id,'workspaceFilterEpisodeType-listbox')]");
    private static final SelenideElement workspaceFilterEpisodeAuthorListBox = $x("//ul[contains(@id,'workspaceFilterEpisodeAuthor-listbox')]");
    private static final SelenideElement workspaceFilterValueIcon = $x("//div[contains(@class,'MuiInputBase-root') and ./div[contains(@class,'MuiChip-root')]]");

    public static void filteringByEpisodeTypes(String... types) {
        for (String type : types) {
            workspaceFilterEpisodeType.shouldBe(visible).click();
            workspaceFilterEpisodeTypeListBox.shouldBe(visible).$x(".//li[contains(text(),'%s')]".formatted(type)).shouldBe(visible).click();
            Assert.assertEquals(type, workspaceFilterValueIcon.getAttribute("title"), "Type isn't applied on filtering");
        }
    }

    public static void filteringByEpisodeAuthors(List<String> authors) {
        for (String author : authors) {
            workspaceFilterEpisodeAuthors.shouldBe(visible).click();
            workspaceFilterEpisodeAuthorListBox.shouldBe(visible).$x(".//li[contains(text(),'%s')]".formatted(author)).shouldBe(visible).click();
            Assert.assertEquals(author, workspaceFilterValueIcon.getAttribute("title"), "Author isn't applied on filtering");
        }
    }
}
