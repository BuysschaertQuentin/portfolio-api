package com.portfolio.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a skill category.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillCategoryRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Icon URL must not exceed 500 characters")
    private String iconUrl;

    private Integer displayOrder;
}
