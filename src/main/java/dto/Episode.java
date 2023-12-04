package dto;

import lombok.Builder;
import lombok.Data;
import utils.DateTimeUtil;

import static utils.DateTimeUtil.DATE_PATTERN_8;
import static utils.DateTimeUtil.DATE_PATTERN_9;

@Data
@Builder
public class Episode {
    @Builder.Default
    private final String author = "Robert Chase MD";
    @Builder.Default
    private final String type = "Deposition";
    @Builder.Default
    private final String date = DateTimeUtil.todayDateInFormat(DATE_PATTERN_8);
    @Builder.Default
    private final String time = DateTimeUtil.todayTimeInFormat(DATE_PATTERN_9);
    private final String notes;
    private final String cpt;
    private final String label;





}