package com.portfolio.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.datamapper.SkillMapper;
import com.portfolio.dto.skill.SkillCategoryRequestDTO;
import com.portfolio.dto.skill.SkillCategoryResponseDTO;
import com.portfolio.dto.skill.SkillRequestDTO;
import com.portfolio.dto.skill.SkillResponseDTO;
import com.portfolio.entity.Skill;
import com.portfolio.entity.SkillCategory;
import com.portfolio.exception.ConflictException;
import com.portfolio.exception.PortfolioException;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ISkillCategoryRepository;
import com.portfolio.repository.ISkillRepository;
import com.portfolio.service.ISkillService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of ISkillService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements ISkillService {

    private final ISkillCategoryRepository categoryRepository;
    private final ISkillRepository skillRepository;
    private final SkillMapper skillMapper;

    // ========== Category Operations ==========

    @Override
    @Transactional(readOnly = true)
    public List<SkillCategoryResponseDTO> getAllCategories() {
        log.debug("Getting all skill categories");
        return categoryRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(skillMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SkillCategoryResponseDTO getCategoryById(UUID categoryId) throws PortfolioException {
        log.debug("Getting skill category by ID: {}", categoryId);

        SkillCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("SkillCategory", "id", categoryId));

        return skillMapper.toResponseDTO(category);
    }

    @Override
    @Transactional
    public SkillCategoryResponseDTO createCategory(SkillCategoryRequestDTO request) throws PortfolioException {
        log.debug("Creating skill category: {}", request.getName());

        if (categoryRepository.existsByName(request.getName())) {
            throw new ConflictException("SkillCategory", "name", request.getName());
        }

        SkillCategory category = new SkillCategory();
        category.setName(request.getName());
        category.setIconUrl(request.getIconUrl());
        category.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        SkillCategory savedCategory = categoryRepository.save(category);
        log.info("Skill category created: {}", savedCategory.getId());

        return skillMapper.toResponseDTO(savedCategory);
    }

    @Override
    @Transactional
    public SkillCategoryResponseDTO updateCategory(UUID categoryId, SkillCategoryRequestDTO request) throws PortfolioException {
        log.debug("Updating skill category: {}", categoryId);

        SkillCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("SkillCategory", "id", categoryId));

        category.setName(request.getName());
        category.setIconUrl(request.getIconUrl());
        if (request.getDisplayOrder() != null) {
            category.setDisplayOrder(request.getDisplayOrder());
        }

        SkillCategory savedCategory = categoryRepository.save(category);
        log.info("Skill category updated: {}", categoryId);

        return skillMapper.toResponseDTO(savedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) throws PortfolioException {
        log.debug("Deleting skill category: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("SkillCategory", "id", categoryId);
        }

        categoryRepository.deleteById(categoryId);
        log.info("Skill category deleted: {}", categoryId);
    }

    // ========== Skill Operations ==========

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDTO> getAllSkills() {
        log.debug("Getting all skills");
        return skillRepository.findAll().stream()
                .map(skillMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDTO> getSkillsByCategory(UUID categoryId) {
        log.debug("Getting skills by category: {}", categoryId);
        return skillRepository.findByCategoryIdOrderByDisplayOrderAsc(categoryId).stream()
                .map(skillMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDTO> getSkillsByType(String type) {
        log.debug("Getting skills by type: {}", type);
        return skillRepository.findByTypeOrderByDisplayOrderAsc(type).stream()
                .map(skillMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDTO> getHighlightedSkills() {
        log.debug("Getting highlighted skills");
        return skillRepository.findByIsHighlightedTrueOrderByDisplayOrderAsc().stream()
                .map(skillMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SkillResponseDTO getSkillById(UUID skillId) throws PortfolioException {
        log.debug("Getting skill by ID: {}", skillId);

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillId));

        return skillMapper.toResponseDTO(skill);
    }

    @Override
    @Transactional
    public SkillResponseDTO createSkill(SkillRequestDTO request) throws PortfolioException {
        log.debug("Creating skill: {}", request.getName());

        SkillCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("SkillCategory", "id", request.getCategoryId()));

        Skill skill = new Skill();
        skill.setCategory(category);
        updateSkillFromRequest(skill, request);

        Skill savedSkill = skillRepository.save(skill);
        log.info("Skill created: {}", savedSkill.getId());

        return skillMapper.toResponseDTO(savedSkill);
    }

    @Override
    @Transactional
    public SkillResponseDTO updateSkill(UUID skillId, SkillRequestDTO request) throws PortfolioException {
        log.debug("Updating skill: {}", skillId);

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillId));

        if (request.getCategoryId() != null && !request.getCategoryId().equals(skill.getCategory().getId())) {
            SkillCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("SkillCategory", "id", request.getCategoryId()));
            skill.setCategory(category);
        }

        updateSkillFromRequest(skill, request);

        Skill savedSkill = skillRepository.save(skill);
        log.info("Skill updated: {}", skillId);

        return skillMapper.toResponseDTO(savedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(UUID skillId) throws PortfolioException {
        log.debug("Deleting skill: {}", skillId);

        if (!skillRepository.existsById(skillId)) {
            throw new ResourceNotFoundException("Skill", "id", skillId);
        }

        skillRepository.deleteById(skillId);
        log.info("Skill deleted: {}", skillId);
    }

    private void updateSkillFromRequest(Skill skill, SkillRequestDTO request) {
        skill.setName(request.getName());
        skill.setType(request.getType());
        skill.setIconUrl(request.getIconUrl());
        skill.setProficiencyLevel(request.getProficiencyLevel());
        skill.setDefinition(request.getDefinition());
        skill.setProfessionalContext(request.getProfessionalContext());
        skill.setSelfCriticism(request.getSelfCriticism());
        skill.setIsHighlighted(request.getIsHighlighted() != null ? request.getIsHighlighted() : false);
        skill.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
    }
}
