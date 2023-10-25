package page.object.portal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class Document {
    private String title;
    private String status;
    private int numPages;

    public Document(String title, int numPages) {
        this.title = title;
        this.numPages = numPages;
    }

    public static List<Document> getExpectedDocumentsList() {
        log.info("Documents are sorted in descending order");
        List<Document> documentList = new ArrayList<>();
        documentList.add(new Document("13153612.pdf", 79));
        documentList.add(new Document("Bestside Medical Group.PDF", 18));
        documentList.add(new Document("Central Bay Medical and Rehab Center (106).PDF", 106));
        documentList.add(new Document("Interventional Pain Experts (72).pdf", 72));
        documentList.add(new Document("Interventional Pain ExpertsMR.pdf", 23));
        documentList.add(new Document("John Davis , M.D. - 4.7.22.pdf", 32));
        documentList.add(new Document("Robert Chase - 03.31.22.pdf", 60));
        documentList.add(new Document("StrongHealth Carrollwood (138).pdf", 138));
        documentList.add(new Document("John Smith - 3.29.22_v2.pdf", 70));
        documentList.add(new Document("Smith Demo Easter.pdf", 1));
        documentList.add(new Document("Smith Demo Fishing.pdf", 1));
        documentList.add(new Document("Smith Demo W2.pdf", 3));
        return documentList.stream().sorted(Comparator.comparing(Document::getNumPages).reversed()).toList();
    }

    public static List<String> getDocumentsListOfTitles() {
        log.info("Documents are sorted in descending order");
        List<Document> documentList = new ArrayList<>();
        documentList.add(new Document("13153612.pdf", 79));
        documentList.add(new Document("Bestside Medical Group.PDF", 18));
        documentList.add(new Document("Central Bay Medical and Rehab Center (106).PDF", 106));
        documentList.add(new Document("Interventional Pain Experts (72).pdf", 72));
        documentList.add(new Document("Interventional Pain ExpertsMR.pdf", 23));
        documentList.add(new Document("John Davis , M.D. - 4.7.22.pdf", 32));
        documentList.add(new Document("Robert Chase - 03.31.22.pdf", 60));
        documentList.add(new Document("StrongHealth Carrollwood (138).pdf", 138));
        documentList.add(new Document("John Smith - 3.29.22_v2.pdf", 70));
        documentList.add(new Document("Smith Demo Easter.pdf", 1));
        documentList.add(new Document("Smith Demo Fishing.pdf", 1));
        documentList.add(new Document("Smith Demo W2.pdf", 3));
        return documentList.stream().sorted(Comparator.comparing(Document::getNumPages).reversed()).map(Document::getTitle).toList();
    }
}
