package com.portfolio.dto.skill;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a skill.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequestDTO {

    private UUID categoryId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @Size(max = 500, message = "Icon URL must not exceed 500 characters")
    private String iconUrl;

    @Min(value = 1, message = "Proficiency level must be at least 1")
    @Max(value = 5, message = "Proficiency level must not exceed 5")
    private Integer proficiencyLevel;

    private String definition;
    private String professionalContext;
    private String selfCriticism;
    private Boolean isHighlighted;
    private Integer displayOrder;
}
