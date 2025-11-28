package org.example.backend.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    ELECTRONICS("Elektronik"),
    SPORT_EQUIPMENT("Sportartikel"),
    COSMETICS("Kosmetik"),
    CLOTHING("Kleidung");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    // Wird als JSON-Wert ausgegeben ("Elektronik" statt ELECTRONICS)
    @JsonValue
    private String getLabel() {
        return label;
    }

    // Ermöglicht das Einlesen von JSON ("Elektronik" → ELECTRONICS)
    @JsonCreator
    private static Category fromLabel(String value) {
        for (Category c : Category.values()) {
            if (c.label.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + value);
    }
}
