package com.portfolio.dto.skill;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for skill category response (with nested skills).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillCategoryResponseDTO {

    private UUID id;
    private String name;
    private String iconUrl;
    private Integer displayOrder;
    private List<SkillResponseDTO> skills;
}
