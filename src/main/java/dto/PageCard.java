package dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class PageCard {
    private String documentTitle;
    private String pageNumber;
    private String date;
    private String time;
    private String tag;

    public static List<PageCard> getPageCard(String title, String date, String time){
        List<PageCard> list = new ArrayList<>();
        list.add(PageCard.builder().documentTitle(title).date(date).time(time).build());
        return list;
    }
}
