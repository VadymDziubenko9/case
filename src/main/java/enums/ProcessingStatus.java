package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessingStatus {
    PENDING("pending"),
    PROCESSING("processing"),
    FINALIZING("finalizing"),
    READY("ready"),
    FAILED("failed"),
    DELETED("deleted");

    private final String displayName;

}
