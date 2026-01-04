package com.portfolio.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.datamapper.ExperienceMapper;
import com.portfolio.dto.experience.ExperienceRequestDTO;
import com.portfolio.dto.experience.ExperienceResponseDTO;
import com.portfolio.entity.Experience;
import com.portfolio.entity.ExperienceSkill;
import com.portfolio.entity.Skill;
import com.portfolio.exception.PortfolioException;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.IExperienceRepository;
import com.portfolio.repository.IExperienceSkillRepository;
import com.portfolio.repository.ISkillRepository;
import com.portfolio.service.IExperienceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of IExperienceService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements IExperienceService {

    private final IExperienceRepository experienceRepository;
    private final IExperienceSkillRepository experienceSkillRepository;
    private final ISkillRepository skillRepository;
    private final ExperienceMapper experienceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponseDTO> getAllExperiences() {
        log.debug("Getting all experiences");
        return experienceRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(experienceMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponseDTO> getVisibleExperiences() {
        log.debug("Getting visible experiences");
        return experienceRepository.findByIsVisibleTrueOrderByDisplayOrderAsc().stream()
                .map(experienceMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponseDTO> getExperiencesByType(String type) {
        log.debug("Getting experiences by type: {}", type);
        return experienceRepository.findByTypeOrderByDisplayOrderAsc(type).stream()
                .map(experienceMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceResponseDTO getExperienceById(UUID experienceId) throws PortfolioException {
        log.debug("Getting experience by ID: {}", experienceId);

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience", "id", experienceId));

        return experienceMapper.toResponseDTO(experience);
    }

    @Override
    @Transactional
    public ExperienceResponseDTO createExperience(ExperienceRequestDTO request) throws PortfolioException {
        log.debug("Creating experience: {}", request.getTitle());

        Experience experience = new Experience();
        updateExperienceFromRequest(experience, request);

        Experience savedExperience = experienceRepository.save(experience);

        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            updateExperienceSkills(savedExperience, request.getSkillIds());
        }

        log.info("Experience created: {}", savedExperience.getId());
        return experienceMapper.toResponseDTO(savedExperience);
    }

    @Override
    @Transactional
    public ExperienceResponseDTO updateExperience(UUID experienceId, ExperienceRequestDTO request) throws PortfolioException {
        log.debug("Updating experience: {}", experienceId);

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience", "id", experienceId));

        updateExperienceFromRequest(experience, request);

        Experience savedExperience = experienceRepository.save(experience);

        if (request.getSkillIds() != null) {
            experienceSkillRepository.deleteByExperienceId(experienceId);
            if (!request.getSkillIds().isEmpty()) {
                updateExperienceSkills(savedExperience, request.getSkillIds());
            }
        }

        log.info("Experience updated: {}", experienceId);
        return experienceMapper.toResponseDTO(savedExperience);
    }

    @Override
    @Transactional
    public void deleteExperience(UUID experienceId) throws PortfolioException {
        log.debug("Deleting experience: {}", experienceId);

        if (!experienceRepository.existsById(experienceId)) {
            throw new ResourceNotFoundException("Experience", "id", experienceId);
        }

        experienceRepository.deleteById(experienceId);
        log.info("Experience deleted: {}", experienceId);
    }

    private void updateExperienceFromRequest(Experience experience, ExperienceRequestDTO request) {
        experience.setType(request.getType());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent() != null ? request.getIsCurrent() : false);
        experience.setTitle(request.getTitle());
        experience.setOrganizationName(request.getOrganizationName());
        experience.setOrganizationLogoUrl(request.getOrganizationLogoUrl());
        experience.setOrganizationUrl(request.getOrganizationUrl());
        experience.setLocation(request.getLocation());
        experience.setDescription(request.getDescription());
        experience.setMissions(request.getMissions());
        experience.setAchievements(request.getAchievements());
        experience.setIsVisible(request.getIsVisible() != null ? request.getIsVisible() : true);
        experience.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
    }

    private void updateExperienceSkills(Experience experience, List<UUID> skillIds) throws PortfolioException {
        for (UUID skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillId));

            ExperienceSkill experienceSkill = new ExperienceSkill();
            experienceSkill.setExperience(experience);
            experienceSkill.setSkill(skill);
            experienceSkillRepository.save(experienceSkill);
        }
    }
}
