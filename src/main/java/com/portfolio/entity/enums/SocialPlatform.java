package com.portfolio.entity.enums;

/**
 * Enum for social platforms.
 */
public enum SocialPlatform {
    LINKEDIN("linkedin"),
    GITHUB("github"),
    TWITTER("twitter"),
    WEBSITE("website"),
    YOUTUBE("youtube"),
    OTHER("other");

    private final String value;

    SocialPlatform(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SocialPlatform fromValue(String value) {
        for (SocialPlatform platform : values()) {
            if (platform.value.equalsIgnoreCase(value)) {
                return platform;
            }
        }
        throw new IllegalArgumentException("Unknown platform: " + value);
    }
}
