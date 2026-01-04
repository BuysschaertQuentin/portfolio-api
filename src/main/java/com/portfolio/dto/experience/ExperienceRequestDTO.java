package com.portfolio.dto.experience;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating an experience.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequestDTO {

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;
    private Boolean isCurrent;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @NotBlank(message = "Organization name is required")
    @Size(max = 200, message = "Organization name must not exceed 200 characters")
    private String organizationName;

    @Size(max = 500, message = "Organization logo URL must not exceed 500 characters")
    private String organizationLogoUrl;

    @Size(max = 500, message = "Organization URL must not exceed 500 characters")
    private String organizationUrl;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    private String description;
    private String missions;
    private String achievements;
    private Boolean isVisible;
    private Integer displayOrder;

    private List<UUID> skillIds;
}
