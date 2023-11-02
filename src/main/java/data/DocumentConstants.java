package data;

import lombok.experimental.UtilityClass;
import page.object.portal.models.Document;

import java.util.List;

@UtilityClass
public class DocumentConstants {
    public static final Document DOCUMENT_13153612_PDF = new Document("13153612.pdf", "ready", 79);
    public static final Document BESTSIDE_MEDICAL_GROUP_PDF = new Document("Bestside Medical Group.PDF", "ready", 18);
    public static final Document CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_PDF = new Document("Central Bay Medical and Rehab Center (106).PDF", "ready", 106);
    public static final Document INTERVENTIONAL_PAIN_EXPERTS_72_PDF = new Document("Interventional Pain Experts (72).pdf", "ready", 72);
    public static final Document INTERVENTIONAL_PAIN_EXPERTS_MR_PDF = new Document("Interventional Pain ExpertsMR.pdf", "ready", 23);
    public static final Document JOHN_DAVIS_PDF = new Document("John Davis , M.D. - 4.7.22.pdf", "ready", 32);
    public static final Document ROBERT_CHASE_PDF = new Document("Robert Chase - 03.31.22.pdf", "ready", 60);
    public static final Document STRONG_HEALTH_CARROLLWOOD_PDF = new Document("StrongHealth Carrollwood (138).pdf", "ready", 138);
    public static final Document JOHN_SMITH_V_2_PDF = new Document("John Smith - 3.29.22_v2.pdf", "ready", 70);
    public static final Document SMITH_DEMO_EASTER_PDF = new Document("Smith Demo Easter.pdf", "ready", 1);
    public static final Document SMITH_DEMO_FISHING_PDF = new Document("Smith Demo Fishing.pdf", "ready", 1);
    public static final Document SMITH_DEMO_W_2_PDF = new Document("Smith Demo W2.pdf", "ready", 3);

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
