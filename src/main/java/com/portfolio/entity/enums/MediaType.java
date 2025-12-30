package com.portfolio.entity.enums;

/**
 * Enum for media types.
 */
public enum MediaType {
    IMAGE("image"),
    VIDEO("video"),
    DOCUMENT("document"),
    SCREENSHOT("screenshot");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MediaType fromValue(String value) {
        for (MediaType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown media type: " + value);
    }
}
