package com.portfolio.datamapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.portfolio.dto.media.MediaResponseDTO;
import com.portfolio.dto.realization.RealizationResponseDTO;
import com.portfolio.dto.skill.SkillResponseDTO;
import com.portfolio.entity.Realization;

import lombok.RequiredArgsConstructor;

/**
 * Mapper for Realization entity <-> DTO conversions.
 */
@Component
@RequiredArgsConstructor
public class RealizationMapper {

    private final SkillMapper skillMapper;
    private final MediaMapper mediaMapper;

    /**
     * Converts Realization entity to RealizationResponseDTO.
     *
     * @param realization the realization entity
     * @return the realization response DTO
     */
    public RealizationResponseDTO toResponseDTO(Realization realization) {
        if (realization == null) {
            return null;
        }

        List<SkillResponseDTO> skills = realization.getRealizationSkills() != null
                ? realization.getRealizationSkills().stream()
                    .map(rs -> skillMapper.toResponseDTO(rs.getSkill()))
                    .toList()
                : null;

        List<MediaResponseDTO> medias = realization.getMedias() != null
                ? realization.getMedias().stream()
                    .map(mediaMapper::toResponseDTO)
                    .toList()
                : null;

        return RealizationResponseDTO.builder()
                .id(realization.getId())
                .experienceId(realization.getExperience() != null ? realization.getExperience().getId() : null)
                .title(realization.getTitle())
                .slug(realization.getSlug())
                .shortDescription(realization.getShortDescription())
                .fullPresentation(realization.getFullPresentation())
                .objectives(realization.getObjectives())
                .context(realization.getContext())
                .stakes(realization.getStakes())
                .steps(realization.getSteps())
                .actorsInteractions(realization.getActorsInteractions())
                .results(realization.getResults())
                .nextSteps(realization.getNextSteps())
                .criticalView(realization.getCriticalView())
                .thumbnailUrl(realization.getThumbnailUrl())
                .demoUrl(realization.getDemoUrl())
                .repoUrl(realization.getRepoUrl())
                .status(realization.getStatus())
                .projectDate(realization.getProjectDate())
                .isFeatured(realization.getIsFeatured())
                .displayOrder(realization.getDisplayOrder())
                .metaTitle(realization.getMetaTitle())
                .metaDescription(realization.getMetaDescription())
                .createdAt(realization.getCreatedAt())
                .updatedAt(realization.getUpdatedAt())
                .skills(skills)
                .medias(medias)
                .build();
    }
}
