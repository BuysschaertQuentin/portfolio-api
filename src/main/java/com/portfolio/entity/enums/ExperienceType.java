package com.portfolio.entity.enums;

/**
 * Enum for experience types.
 */
public enum ExperienceType {
    JOB("job"),
    INTERNSHIP("internship"),
    APPRENTICESHIP("apprenticeship"),
    TRAINING("training"),
    CERTIFICATION("certification");

    private final String value;

    ExperienceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ExperienceType fromValue(String value) {
        for (ExperienceType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown experience type: " + value);
    }
}
