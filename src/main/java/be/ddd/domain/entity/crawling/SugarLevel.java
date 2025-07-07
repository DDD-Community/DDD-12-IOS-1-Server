package be.ddd.domain.entity.crawling;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SugarLevel {
    ZERO("무당", 0),
    LOW("저당", 20),
    HIGH("고당", Integer.MAX_VALUE);

    private final String description;
    private final int maxSugarGrams;

    public static SugarLevel valueOfSugar(Integer sugar) {
        if (sugar == null) {
            return HIGH;
        }
        return Arrays.stream(values())
                .filter(level -> sugar <= level.maxSugarGrams)
                .findFirst()
                .orElse(HIGH);
    }

    public static Optional<SugarLevel> fromParam(String param) {
        if (param == null || param.isBlank()) {
            return Optional.empty();
        }
        switch (param.trim().toUpperCase()) {
            case "ZERO":
                return Optional.of(ZERO);

            case "LOW":
                return Optional.of(LOW);

            case "HIGH":
                return Optional.of(HIGH);

            default:
                return Optional.empty();
        }
    }
}
