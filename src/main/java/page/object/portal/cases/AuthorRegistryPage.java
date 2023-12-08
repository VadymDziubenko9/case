package page.object.portal.cases;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static page.object.portal.cases.BaseAbstractPage.closeAllBubbles;
import static page.object.portal.cases.BaseAbstractPage.waitTillBubbleMessageShown;
import static utils.JsUtil.waitForDomToLoad;

public class AuthorRegistryPage {
    private final SelenideElement searchInput = $x("//input[@name='search']");
    private final String DROP_DOWN_CONTEXT_MENU_BTN = "//button[contains(@data-action-button,'authorDropdow')]";
    private final String DROP_DOWN_CONTEXT_MENU_LIST = "//ul/li[contains(@data-action-menu-item,'%s')]";
    private final SelenideElement authorElementLoc = $x("//ul/li[contains(@class,'MuiListItem-root')]");
    private final SelenideElement noAuthorsFoundLoc = $x("//div[contains(@class,'MuiBox-root') and ./p[text()='No author(s)']]");

    private final ElementsCollection listOfAuthors = $$x("//ul/li[contains(@class,'MuiListItem-root')]");

    public AuthorRegistryPage searchAuthor(String authorName) {
        searchInput.shouldBe(visible).sendKeys(authorName);
        waitForDomToLoad();
        if (noAuthorsFoundLoc.isDisplayed()) return null;
        else {
            authorElementLoc.shouldBe(visible).shouldHave(Condition.matchText(authorName));
        }
        return this;
    }

    public AuthorRegistryPage markAuthorVerified(String authorName) {
        expandAuthorContextMenu(authorName);
        $x(DROP_DOWN_CONTEXT_MENU_LIST.formatted("markVerifiedAuthor")).click();
        waitTillBubbleMessageShown("Episode author is updated");
        closeAllBubbles();
        return this;
    }

    public boolean verifyIfVerifiedIconIsAppeared(String authorName) {
        return authorElementLoc.$x(".//span[contains(text(),'%s') and ./*[@aria-label='Verified']]".formatted(authorName)).isDisplayed();
    }

    public String getAuthorName() {
        return authorElementLoc.$x("./div/span").shouldBe(visible).getText();
    }

    public void deleteAuthor(String name) {
        searchAuthor(name);
        expandAuthorContextMenu(name);
        $x(DROP_DOWN_CONTEXT_MENU_LIST.formatted("deleteAuthor")).click();
        waitTillBubbleMessageShown("Episode author is deleted");
        closeAllBubbles();
    }

    private void expandAuthorContextMenu(String name) {
        authorElementLoc
                .$x(".//div[contains(@class,'MuiListItemText-root') and .//text()='%s']//..".formatted(name) + DROP_DOWN_CONTEXT_MENU_BTN)
                .shouldBe(enabled)
                .click();
    }

    public int getSizeOfListOfFoundAuthor() {
        return listOfAuthors.asFixedIterable().stream().toList().size();
    }
}
