package page.object.portal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.DateTimeUtil;

import static utils.DateTimeUtil.DATE_PATTERN_8;
import static utils.DateTimeUtil.DATE_PATTERN_9;

@Data
@Builder
@AllArgsConstructor
public class Episode {
    @Builder.Default
    private final String author = "John Smith " + System.currentTimeMillis();
    @Builder.Default
    private final String type = "CAdventHeal Hk Carrollwood " + System.currentTimeMillis();
    @Builder.Default
    private final String date = DateTimeUtil.todayDateInFormat(DATE_PATTERN_8);
    @Builder.Default
    private final String time = DateTimeUtil.todayTimeInFormat(DATE_PATTERN_9);
    @Builder.Default
    private final String notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + System.currentTimeMillis();
    private final String cpt;
    private final String label;
}
