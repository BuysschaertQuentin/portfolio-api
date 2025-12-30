package com.portfolio.entity.enums;

/**
 * Enum for realization/project statuses.
 */
public enum RealizationStatus {
    DRAFT("draft"),
    PUBLISHED("published"),
    ARCHIVED("archived"),
    PRIVATE("private");

    private final String value;

    RealizationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RealizationStatus fromValue(String value) {
        for (RealizationStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown realization status: " + value);
    }
}
