package com.portfolio.datamapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.portfolio.dto.experience.ExperienceResponseDTO;
import com.portfolio.dto.skill.SkillResponseDTO;
import com.portfolio.entity.Experience;

import lombok.RequiredArgsConstructor;

/**
 * Mapper for Experience entity <-> DTO conversions.
 */
@Component
@RequiredArgsConstructor
public class ExperienceMapper {

    private final SkillMapper skillMapper;

    /**
     * Converts Experience entity to ExperienceResponseDTO.
     *
     * @param experience the experience entity
     * @return the experience response DTO
     */
    public ExperienceResponseDTO toResponseDTO(Experience experience) {
        if (experience == null) {
            return null;
        }

        List<SkillResponseDTO> skills = experience.getExperienceSkills() != null
                ? experience.getExperienceSkills().stream()
                    .map(es -> skillMapper.toResponseDTO(es.getSkill()))
                    .toList()
                : null;

        return ExperienceResponseDTO.builder()
                .id(experience.getId())
                .type(experience.getType())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrent(experience.getIsCurrent())
                .title(experience.getTitle())
                .organizationName(experience.getOrganizationName())
                .organizationLogoUrl(experience.getOrganizationLogoUrl())
                .organizationUrl(experience.getOrganizationUrl())
                .location(experience.getLocation())
                .description(experience.getDescription())
                .missions(experience.getMissions())
                .achievements(experience.getAchievements())
                .isVisible(experience.getIsVisible())
                .displayOrder(experience.getDisplayOrder())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .skills(skills)
                .build();
    }
}
