package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagOps {
    LEGAL("Legal"),
    BILLS("Bills"),
    MISC("Misc"),
    NO_TAG("noTag");

    private final String name;
}
