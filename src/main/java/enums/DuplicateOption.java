package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Getter
@AllArgsConstructor
public enum DuplicateOption {
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
        return Arrays.stream(DuplicateOption.values()).map(str -> str.name).toList();
    }
}
