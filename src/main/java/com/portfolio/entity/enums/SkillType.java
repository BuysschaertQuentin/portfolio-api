package com.portfolio.entity.enums;

/**
 * Enum for skill types.
 */
public enum SkillType {
    SOFT_SKILL("soft_skill"),
    HARD_SKILL("hard_skill");

    private final String value;

    SkillType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SkillType fromValue(String value) {
        for (SkillType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown skill type: " + value);
    }
}
