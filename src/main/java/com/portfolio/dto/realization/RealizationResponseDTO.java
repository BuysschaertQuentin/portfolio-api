package com.portfolio.dto.realization;

import com.portfolio.dto.media.MediaResponseDTO;
import com.portfolio.dto.skill.SkillResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO for realization response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealizationResponseDTO {

    private UUID id;
    private UUID experienceId;
    private String title;
    private String slug;
    private String shortDescription;
    private String fullPresentation;
    private String objectives;
    private String context;
    private String stakes;
    private String steps;
    private String actorsInteractions;
    private String results;
    private String nextSteps;
    private String criticalView;
    private String thumbnailUrl;
    private String demoUrl;
    private String repoUrl;
    private String status;
    private LocalDate projectDate;
    private Boolean isFeatured;
    private Integer displayOrder;
    private String metaTitle;
    private String metaDescription;
    private Instant createdAt;
    private Instant updatedAt;
    private List<SkillResponseDTO> skills;
    private List<MediaResponseDTO> medias;
}
