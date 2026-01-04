package com.portfolio.service.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.datamapper.RealizationMapper;
import com.portfolio.dto.realization.RealizationRequestDTO;
import com.portfolio.dto.realization.RealizationResponseDTO;
import com.portfolio.entity.Experience;
import com.portfolio.entity.Realization;
import com.portfolio.entity.RealizationSkill;
import com.portfolio.entity.Skill;
import com.portfolio.exception.ConflictException;
import com.portfolio.exception.PortfolioException;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.IExperienceRepository;
import com.portfolio.repository.IRealizationRepository;
import com.portfolio.repository.IRealizationSkillRepository;
import com.portfolio.repository.ISkillRepository;
import com.portfolio.service.IRealizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of IRealizationService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealizationServiceImpl implements IRealizationService {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private final IRealizationRepository realizationRepository;
    private final IRealizationSkillRepository realizationSkillRepository;
    private final IExperienceRepository experienceRepository;
    private final ISkillRepository skillRepository;
    private final RealizationMapper realizationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RealizationResponseDTO> getAllRealizations() {
        log.debug("Getting all realizations");
        return realizationRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(realizationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RealizationResponseDTO> getPublishedRealizations() {
        log.debug("Getting published realizations");
        return realizationRepository.findAllPublished().stream()
                .map(realizationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RealizationResponseDTO> getFeaturedRealizations() {
        log.debug("Getting featured realizations");
        return realizationRepository.findFeaturedPublished().stream()
                .map(realizationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RealizationResponseDTO> getRealizationsBySkill(UUID skillId) {
        log.debug("Getting realizations by skill: {}", skillId);
        return realizationRepository.findPublishedBySkillId(skillId).stream()
                .map(realizationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RealizationResponseDTO getRealizationById(UUID realizationId) throws PortfolioException {
        log.debug("Getting realization by ID: {}", realizationId);

        Realization realization = realizationRepository.findById(realizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Realization", "id", realizationId));

        return realizationMapper.toResponseDTO(realization);
    }

    @Override
    @Transactional(readOnly = true)
    public RealizationResponseDTO getRealizationBySlug(String slug) throws PortfolioException {
        log.debug("Getting realization by slug: {}", slug);

        Realization realization = realizationRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Realization", "slug", slug));

        return realizationMapper.toResponseDTO(realization);
    }

    @Override
    @Transactional
    public RealizationResponseDTO createRealization(RealizationRequestDTO request) throws PortfolioException {
        log.debug("Creating realization: {}", request.getTitle());

        String slug = request.getSlug() != null ? request.getSlug() : generateSlug(request.getTitle());
        if (realizationRepository.existsBySlug(slug)) {
            throw new ConflictException("Realization", "slug", slug);
        }

        Realization realization = new Realization();
        realization.setSlug(slug);
        updateRealizationFromRequest(realization, request);

        Realization savedRealization = realizationRepository.save(realization);

        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            updateRealizationSkills(savedRealization, request.getSkillIds());
        }

        log.info("Realization created: {}", savedRealization.getId());
        return realizationMapper.toResponseDTO(savedRealization);
    }

    @Override
    @Transactional
    public RealizationResponseDTO updateRealization(UUID realizationId, RealizationRequestDTO request) throws PortfolioException {
        log.debug("Updating realization: {}", realizationId);

        Realization realization = realizationRepository.findById(realizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Realization", "id", realizationId));

        if (request.getSlug() != null && !request.getSlug().equals(realization.getSlug())) {
            if (realizationRepository.existsBySlug(request.getSlug())) {
                throw new ConflictException("Realization", "slug", request.getSlug());
            }
            realization.setSlug(request.getSlug());
        }

        updateRealizationFromRequest(realization, request);

        Realization savedRealization = realizationRepository.save(realization);

        if (request.getSkillIds() != null) {
            realizationSkillRepository.deleteByRealizationId(realizationId);
            if (!request.getSkillIds().isEmpty()) {
                updateRealizationSkills(savedRealization, request.getSkillIds());
            }
        }

        log.info("Realization updated: {}", realizationId);
        return realizationMapper.toResponseDTO(savedRealization);
    }

    @Override
    @Transactional
    public void deleteRealization(UUID realizationId) throws PortfolioException {
        log.debug("Deleting realization: {}", realizationId);

        if (!realizationRepository.existsById(realizationId)) {
            throw new ResourceNotFoundException("Realization", "id", realizationId);
        }

        realizationRepository.deleteById(realizationId);
        log.info("Realization deleted: {}", realizationId);
    }

    private void updateRealizationFromRequest(Realization realization, RealizationRequestDTO request) throws PortfolioException {
        if (request.getExperienceId() != null) {
            Experience experience = experienceRepository.findById(request.getExperienceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Experience", "id", request.getExperienceId()));
            realization.setExperience(experience);
        } else {
            realization.setExperience(null);
        }

        realization.setTitle(request.getTitle());
        realization.setShortDescription(request.getShortDescription());
        realization.setFullPresentation(request.getFullPresentation());
        realization.setObjectives(request.getObjectives());
        realization.setContext(request.getContext());
        realization.setStakes(request.getStakes());
        realization.setSteps(request.getSteps());
        realization.setActorsInteractions(request.getActorsInteractions());
        realization.setResults(request.getResults());
        realization.setNextSteps(request.getNextSteps());
        realization.setCriticalView(request.getCriticalView());
        realization.setThumbnailUrl(request.getThumbnailUrl());
        realization.setDemoUrl(request.getDemoUrl());
        realization.setRepoUrl(request.getRepoUrl());
        realization.setStatus(request.getStatus() != null ? request.getStatus() : "draft");
        realization.setProjectDate(request.getProjectDate());
        realization.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        realization.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
        realization.setMetaTitle(request.getMetaTitle());
        realization.setMetaDescription(request.getMetaDescription());
    }

    private void updateRealizationSkills(Realization realization, List<UUID> skillIds) throws PortfolioException {
        for (UUID skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillId));

            RealizationSkill realizationSkill = new RealizationSkill();
            realizationSkill.setRealization(realization);
            realizationSkill.setSkill(skill);
            realizationSkillRepository.save(realizationSkill);
        }
    }

    private String generateSlug(String title) {
        String nowhitespace = WHITESPACE.matcher(title).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
