package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagOps {
    LEGAL("Legal"),
    BILLS("Bills"),
    NO_TAG("noTag");

    private final String name;
}
