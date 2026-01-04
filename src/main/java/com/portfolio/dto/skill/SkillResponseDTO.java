package com.portfolio.dto.skill;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for skill response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponseDTO {

    private UUID id;
    private UUID categoryId;
    private String categoryName;
    private String name;
    private String type;
    private String iconUrl;
    private Integer proficiencyLevel;
    private String definition;
    private String professionalContext;
    private String selfCriticism;
    private Boolean isHighlighted;
    private Integer displayOrder;
    private Instant createdAt;
    private Instant updatedAt;
}
