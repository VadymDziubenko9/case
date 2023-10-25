package page.object.portal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.DateTimeUtil;

import java.util.Random;

import static utils.DateTimeUtil.DATE_PATTERN_8;
import static utils.DateTimeUtil.DATE_PATTERN_9;

@Data
@Builder
@AllArgsConstructor
public class Episode {
    private static Random random = new Random();
    private static StringBuilder sb = new StringBuilder();

    @Builder.Default
    private final String author = "John Smith " + System.currentTimeMillis();
    @Builder.Default
    private final String type = "CAdventHeal Hk Carrollwood " + System.currentTimeMillis();
    @Builder.Default
    private final String date = DateTimeUtil.todayDateInFormat(DATE_PATTERN_8);
    @Builder.Default
    private final String time = DateTimeUtil.todayTimeInFormat(DATE_PATTERN_9);
    @Builder.Default
    private final String notes = generateRandomLoremIpsum(25);
    private final String cpt;
    private final String label;



    private static final String[] WORDS = {
            "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed", "do", "eiusmod", "tempor",
            "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua", "Ut", "enim", "ad", "minim", "veniam", "quis",
            "nostrud", "exercitation", "ullamco", "laboris", "nisi", "ut", "aliquip", "ex", "ea", "commodo", "consequat",
            "Duis", "aute", "irure", "dolor", "in", "reprehenderit", "in", "voluptate", "velit", "esse", "cillum", "dolore",
            "eu", "fugiat", "nulla", "pariatur", "Excepteur", "sint", "occaecat", "cupidatat", "non", "proident",
            "sunt", "in", "culpa", "qui", "officia", "deserunt", "mollit", "anim", "id", "est", "laborum"
    };

    public static String generateRandomLoremIpsum(int wordCount) {
        for (int i = 0; i < wordCount; i++) {
            int index = random.nextInt(WORDS.length);
            sb.append(WORDS[index]).append(" ");
        }
        return sb.toString().trim();
    }
}
