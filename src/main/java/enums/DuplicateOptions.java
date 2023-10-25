package enums;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@AllArgsConstructor

public enum DuplicateOptions {
    EPISODE_LABELS("Episode labels"),
    EPISODE_NOTES("Episode notes"),
    EPISODE_FACTS("Episode facts"),
    PAGE_TAG("Page tag"),
    PAGE_COLOR("Page colors"),
    PAGE_IN_WORKSPACE("Page in workspace"),
    PAGE_HIGHLIGHTS("Page highlights"),
    DOCUMENT_LABEL("Document labels");

    private final String name;

    public static List<String> getExpectedOptionsList() {
        List<DuplicateOptions> list = new ArrayList<>();
        list.add(EPISODE_LABELS);
        list.add(EPISODE_NOTES);
        list.add(EPISODE_FACTS);
        list.add(PAGE_TAG);
        list.add(PAGE_COLOR);
        list.add(PAGE_IN_WORKSPACE);
        list.add(PAGE_HIGHLIGHTS);
        list.add(DOCUMENT_LABEL);
        return list.stream().map(str -> str.name).toList();
    }
}
