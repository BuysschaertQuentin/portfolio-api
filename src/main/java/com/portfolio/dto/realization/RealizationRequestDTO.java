package com.portfolio.dto.realization;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a realization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealizationRequestDTO {

    private UUID experienceId;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 200, message = "Slug must not exceed 200 characters")
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

    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    private String thumbnailUrl;

    @Size(max = 500, message = "Demo URL must not exceed 500 characters")
    private String demoUrl;

    @Size(max = 500, message = "Repo URL must not exceed 500 characters")
    private String repoUrl;

    private String status;
    private LocalDate projectDate;
    private Boolean isFeatured;
    private Integer displayOrder;

    @Size(max = 200, message = "Meta title must not exceed 200 characters")
    private String metaTitle;

    private String metaDescription;

    private List<UUID> skillIds;
}
