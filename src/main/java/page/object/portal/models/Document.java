package page.object.portal.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {
    private String title;
    @Builder.Default
    private String status = "ready";
    private int numPages;
}
