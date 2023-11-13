package page.object.portal.models;

import lombok.Builder;
import lombok.Data;
import utils.DateTimeUtil;

import static utils.DateTimeUtil.DATE_PATTERN_8;

@Data
@Builder
public class Episode {
    @Builder.Default
    private final String author = "Robert Chase MD";
    @Builder.Default
    private final String type = "Deposition";
    @Builder.Default
    private final String date = DateTimeUtil.todayDateInFormat(DATE_PATTERN_8);
    private final String time;
    private final String notes;
    private final String cpt;
    private final String label;





}