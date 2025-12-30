package com.portfolio.entity.enums;

/**
 * Enum for user availability status.
 */
public enum DisponibilityStatus {
    AVAILABLE("available"),
    NOT_AVAILABLE("not_available"),
    AVAILABLE_FROM("available_from");

    private final String value;

    DisponibilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DisponibilityStatus fromValue(String value) {
        for (DisponibilityStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown disponibility status: " + value);
    }
}
