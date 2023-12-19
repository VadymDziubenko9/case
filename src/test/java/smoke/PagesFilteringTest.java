package smoke;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.object.portal.cases.LoginPage;

import static constants.DocumentConstants.*;
import static constants.EpisodeConstants.*;
import static enums.TagOps.BILLS;
import static enums.TagOps.LEGAL;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Config.USER_NAME;
import static utils.Config.USER_PASSWORD;
import static widgets.ColorWidget.filterPagesByColors;
import static widgets.ListboxFilterWidget.*;
import static widgets.TagWidget.filterPagesByTags;

public class PagesFilteringTest extends BaseAbstractTest {
    private static final String RED_COLOR = "red";
    private static final String BLUE_GREY_COLOR = "blueGrey";
    private static final String GREEN_COLOR = "green";

    @BeforeClass(alwaysRun = true)
    public void login() {
        new LoginPage().login(USER_NAME, USER_PASSWORD);
    }

    @Test(priority = 1)
    @Description("Verify the filtering of pages in Workspace view based on a single episode type")
    public void verifySingleEpisodeTypeFiltering() {
        openWorkspace();
        filteringByEpisodeTypes(DEPOSITION_TYPE);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 3").isEqualTo(3);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 152").isEqualTo(152);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsBySingleType());
            softAssertions.assertThat(verifyWhetherSelectedTypeIsNotAvailableForTheSecondTime(DEPOSITION_TYPE))
                    .as("Type '%s' is duplicated in Workspace filtering".formatted(DEPOSITION_TYPE)).isTrue();
        });
    }

    @Test(priority = 2)
    @Description("Verify the filtering of pages in Workspace view based on multiple episode types")
    public void verifyMultipleEpisodeTypesFiltering() {
        openWorkspace();
        filteringByEpisodeTypes(DEPOSITION_TYPE, MEDICAL_TYPE, MRI_TYPE);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 7").isEqualTo(7);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 165").isEqualTo(165);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsByMultipleTypes());
            softAssertions.assertThat(verifyWhetherSelectedTypeIsNotAvailableForTheSecondTime(DEPOSITION_TYPE))
                    .as("Type '%s' is duplicated in Workspace filtering".formatted(DEPOSITION_TYPE)).isTrue();
            softAssertions.assertThat(verifyWhetherSelectedTypeIsNotAvailableForTheSecondTime(MEDICAL_TYPE))
                    .as("Type '%s' is duplicated in Workspace filtering".formatted(MEDICAL_TYPE)).isTrue();
            softAssertions.assertThat(verifyWhetherSelectedTypeIsNotAvailableForTheSecondTime(MRI_TYPE))
                    .as("Type '%s' is duplicated in Workspace filtering".formatted(MRI_TYPE)).isTrue();
        });
    }

    @Test(priority = 3)
    @Description("Verify the filtering of pages in Workspace view based on single episode author")
    public void verifySingleEpisodeAuthorFiltering() {
        openWorkspace();
        filteringByEpisodeAuthors(LAWERNCE_KUTNER_AUTHOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 2").isEqualTo(2);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 3").isEqualTo(3);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsBySingleAuthor());
            softAssertions.assertThat(verifyWhetherSelectedAuthorIsNotAvailableForTheSecondTime(LAWERNCE_KUTNER_AUTHOR))
                    .as("Author '%s' is duplicated in Workspace filtering".formatted(LAWERNCE_KUTNER_AUTHOR))
                    .isTrue();
        });
    }

    @Test(priority = 4)
    @Description("Verify the filtering of pages in Workspace view based on multiple episode authors")
    public void verifyMultipleEpisodeAuthorsFiltering() {
        openWorkspace();
        filteringByEpisodeAuthors(BUDDY_BOMBER_AUTHOR, CHRISTOPHER_JACOBSON_AUTHOR, CLAIMANT_AUTHOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 6").isEqualTo(6);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 130").isEqualTo(130);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsByMultipleAuthors());
            softAssertions.assertThat(verifyWhetherSelectedAuthorIsNotAvailableForTheSecondTime(BUDDY_BOMBER_AUTHOR))
                    .as("Author '%s' is duplicated in Workspace filtering".formatted(BUDDY_BOMBER_AUTHOR))
                    .isTrue();
            softAssertions.assertThat(verifyWhetherSelectedAuthorIsNotAvailableForTheSecondTime(CHRISTOPHER_JACOBSON_AUTHOR))
                    .as("Author '%s' is duplicated in Workspace filtering".formatted(CHRISTOPHER_JACOBSON_AUTHOR))
                    .isTrue();
            softAssertions.assertThat(verifyWhetherSelectedAuthorIsNotAvailableForTheSecondTime(CLAIMANT_AUTHOR))
                    .as("Author '%s' is duplicated in Workspace filtering".formatted(CLAIMANT_AUTHOR))
                    .isTrue();
        });
    }

    @Test(priority = 5)
    @Description("Verify the filtering of pages in Workspace view based on single and existing color")
    public void verifySingleColorFiltering() {
        openWorkspace();
        filterPagesByColors(BLUE_GREY_COLOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 7").isEqualTo(7);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 15").isEqualTo(15);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsBySingleColor());
        });
    }

    @Test(priority = 6)
    @Description("Verify the filtering of pages in Workspace view based on multiple colors")
    public void verifyMultipleColorFiltering() {
        openWorkspace();
        filterPagesByColors(RED_COLOR, BLUE_GREY_COLOR);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 8").isEqualTo(8);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 18").isEqualTo(18);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsByMultipleColors());
        });
    }

    @Test(priority = 7)
    @Description("Verify the filtering of pages in Workspace view based on single and non-existing color")
    public void verifyNonExistingColorFiltering() {
        openWorkspace();
        filterPagesByColors(GREEN_COLOR);
        assertThat(workspacePage.verifyEmptyWorkspace()).as("Workspace should be empty").isEqualTo("No page(s) in workspace");
    }


    @Test(priority = 8)
    @Description("Verify the filtering of pages in Workspace view based on single and non-existing color")
    public void verifySingleTagFiltering() {
        openWorkspace();
        filterPagesByTags(LEGAL.getName());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 3").isEqualTo(3);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 152").isEqualTo(152);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsBySingleType());
        });
    }

    @Test(priority = 9)
    @Description("Verify the filtering of pages in Workspace view based on single and non-existing color")
    public void verifyMultipleTagsFiltering() {
        openWorkspace();
        filterPagesByTags(LEGAL.getName(), BILLS.getName());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(workspacePage.getTotalNumberOfPages()).as("Total number of pages: 4").isEqualTo(4);
            softAssertions.assertThat(workspacePage.getTotalNumberOfPagesIncludingStapled()).as("Including stapled pages: 155").isEqualTo(155);
            softAssertions.assertThat(workspacePage.getListOfDisplayedDocumentsTitle())
                    .as("Wrong documents are displayed")
                    .containsExactlyInAnyOrderElementsOf(getListOfExpectedFilteredDocumentsByMultipleTags());
        });
    }

    @Step("Open Workspace view")
    public void openWorkspace() {
        homePage.openHomePage().openCase(COPIED_CASE_NAME).openWorkspace();
    }
}
