package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Team {
    QA_TEAM("QA Team", "This team is for testing purpose", List.of("Otto von Bismarck"));

    private final String name;
    private final String description;
    private final List<String> members;
}
