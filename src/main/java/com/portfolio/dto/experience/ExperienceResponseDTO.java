package com.portfolio.dto.experience;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.portfolio.dto.skill.SkillResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for experience response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponseDTO {

    private UUID id;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String title;
    private String organizationName;
    private String organizationLogoUrl;
    private String organizationUrl;
    private String location;
    private String description;
    private String missions;
    private String achievements;
    private Boolean isVisible;
    private Integer displayOrder;
    private Instant createdAt;
    private Instant updatedAt;
    private List<SkillResponseDTO> skills;
}
