package com.portfolio.datamapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.portfolio.dto.skill.SkillCategoryResponseDTO;
import com.portfolio.dto.skill.SkillResponseDTO;
import com.portfolio.entity.Skill;
import com.portfolio.entity.SkillCategory;

import lombok.RequiredArgsConstructor;

/**
 * Mapper for Skill and SkillCategory entity <-> DTO conversions.
 */
@Component
@RequiredArgsConstructor
public class SkillMapper {

    /**
     * Converts Skill entity to SkillResponseDTO.
     *
     * @param skill the skill entity
     * @return the skill response DTO
     */
    public SkillResponseDTO toResponseDTO(Skill skill) {
        if (skill == null) {
            return null;
        }

        return SkillResponseDTO.builder()
                .id(skill.getId())
                .categoryId(skill.getCategory() != null ? skill.getCategory().getId() : null)
                .categoryName(skill.getCategory() != null ? skill.getCategory().getName() : null)
                .name(skill.getName())
                .type(skill.getType())
                .iconUrl(skill.getIconUrl())
                .proficiencyLevel(skill.getProficiencyLevel())
                .definition(skill.getDefinition())
                .professionalContext(skill.getProfessionalContext())
                .selfCriticism(skill.getSelfCriticism())
                .isHighlighted(skill.getIsHighlighted())
                .displayOrder(skill.getDisplayOrder())
                .createdAt(skill.getCreatedAt())
                .updatedAt(skill.getUpdatedAt())
                .build();
    }

    /**
     * Converts SkillCategory entity to SkillCategoryResponseDTO.
     *
     * @param category the skill category entity
     * @return the skill category response DTO
     */
    public SkillCategoryResponseDTO toResponseDTO(SkillCategory category) {
        if (category == null) {
            return null;
        }

        List<SkillResponseDTO> skills = category.getSkills() != null
                ? category.getSkills().stream()
                    .map(this::toResponseDTO)
                    .toList()
                : null;

        return SkillCategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .iconUrl(category.getIconUrl())
                .displayOrder(category.getDisplayOrder())
                .skills(skills)
                .build();
    }
}
