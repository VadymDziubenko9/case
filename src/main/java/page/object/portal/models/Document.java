package page.object.portal.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {
    private String title;
    private String status;
    private int numPages;
}
