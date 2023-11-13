package data;

import lombok.experimental.UtilityClass;
import page.object.portal.models.Document;

import java.util.List;

@UtilityClass
public class DocumentConstants {
    public static final Document DOCUMENT_13153612_PDF = Document.builder().title("13153612.pdf").numPages(79).build();
    public static final Document BESTSIDE_MEDICAL_GROUP_PDF = Document.builder().title("Bestside Medical Group.PDF").numPages(18).build();
    public static final Document CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF = Document.builder().title("Central Bay Medical and Rehab Center (106).PDF").numPages(106).build();
    public static final Document INTERVENTIONAL_PAIN_EXPERTS_72_PDF = Document.builder().title("Interventional Pain Experts (72).pdf").numPages(72).build();
    public static final Document INTERVENTIONAL_PAIN_EXPERTS_MR_PDF = Document.builder().title("Interventional Pain ExpertsMR.pdf").numPages(23).build();
    public static final Document JOHN_DAVIS_PDF = Document.builder().title("John Davis , M.D. - 4.7.22.pdf").numPages(32).build();
    public static final Document ROBERT_CHASE_PDF = Document.builder().title("Robert Chase - 03.31.22.pdf").numPages(60).build();
    public static final Document STRONG_HEALTH_CARROLLWOOD_PDF = Document.builder().title("StrongHealth Carrollwood (138).pdf").numPages(138).build();
    public static final Document JOHN_SMITH_V_2_PDF = Document.builder().title("John Smith - 3.29.22_v2.pdf").numPages(70).build();
    public static final Document SMITH_DEMO_EASTER_PDF = Document.builder().title("Smith Demo Easter.pdf").numPages(1).build();
    public static final Document SMITH_DEMO_FISHING_PDF = Document.builder().title("Smith Demo Fishing.pdf").numPages(1).build();
    public static final Document SMITH_DEMO_W_2_PDF = Document.builder().title("Smith Demo W2.pdf").numPages(3).build();

    public static List<Document> getExpectedDocumentsList() {
        return List.of(DOCUMENT_13153612_PDF,
                BESTSIDE_MEDICAL_GROUP_PDF,
                CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF,
                INTERVENTIONAL_PAIN_EXPERTS_72_PDF,
                INTERVENTIONAL_PAIN_EXPERTS_MR_PDF,
                JOHN_DAVIS_PDF,
                ROBERT_CHASE_PDF,
                STRONG_HEALTH_CARROLLWOOD_PDF,
                JOHN_SMITH_V_2_PDF,
                SMITH_DEMO_EASTER_PDF,
                SMITH_DEMO_FISHING_PDF,
                SMITH_DEMO_W_2_PDF);
    }

    public static List<String> getDocumentsListOfTitles() {
        return getExpectedDocumentsList().stream().map(Document::getTitle).toList();
    }
}
