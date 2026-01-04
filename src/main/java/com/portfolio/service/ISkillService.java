package com.portfolio.service;

import java.util.List;
import java.util.UUID;

import com.portfolio.dto.skill.SkillCategoryRequestDTO;
import com.portfolio.dto.skill.SkillCategoryResponseDTO;
import com.portfolio.dto.skill.SkillRequestDTO;
import com.portfolio.dto.skill.SkillResponseDTO;
import com.portfolio.exception.PortfolioException;

/**
 * Service interface for Skill and SkillCategory operations.
 */
public interface ISkillService {

    // ========== Category Operations ==========

    /**
     * Gets all skill categories with their skills.
     *
     * @return list of skill categories
     */
    List<SkillCategoryResponseDTO> getAllCategories();

    /**
     * Gets a skill category by ID.
     *
     * @param categoryId the category ID
     * @return the category response
     * @throws PortfolioException if category not found
     */
    SkillCategoryResponseDTO getCategoryById(UUID categoryId) throws PortfolioException;

    /**
     * Creates a new skill category.
     *
     * @param request the category data
     * @return the created category response
     * @throws PortfolioException if category already exists
     */
    SkillCategoryResponseDTO createCategory(SkillCategoryRequestDTO request) throws PortfolioException;

    /**
     * Updates an existing skill category.
     *
     * @param categoryId the category ID
     * @param request    the category data
     * @return the updated category response
     * @throws PortfolioException if category not found
     */
    SkillCategoryResponseDTO updateCategory(UUID categoryId, SkillCategoryRequestDTO request) throws PortfolioException;

    /**
     * Deletes a skill category.
     *
     * @param categoryId the category ID
     * @throws PortfolioException if category not found
     */
    void deleteCategory(UUID categoryId) throws PortfolioException;

    // ========== Skill Operations ==========

    /**
     * Gets all skills.
     *
     * @return list of skills
     */
    List<SkillResponseDTO> getAllSkills();

    /**
     * Gets skills by category.
     *
     * @param categoryId the category ID
     * @return list of skills in the category
     */
    List<SkillResponseDTO> getSkillsByCategory(UUID categoryId);

    /**
     * Gets skills by type (soft_skill or hard_skill).
     *
     * @param type the skill type
     * @return list of skills of the specified type
     */
    List<SkillResponseDTO> getSkillsByType(String type);

    /**
     * Gets highlighted skills.
     *
     * @return list of highlighted skills
     */
    List<SkillResponseDTO> getHighlightedSkills();

    /**
     * Gets a skill by ID.
     *
     * @param skillId the skill ID
     * @return the skill response
     * @throws PortfolioException if skill not found
     */
    SkillResponseDTO getSkillById(UUID skillId) throws PortfolioException;

    /**
     * Creates a new skill.
     *
     * @param request the skill data
     * @return the created skill response
     * @throws PortfolioException if operation fails
     */
    SkillResponseDTO createSkill(SkillRequestDTO request) throws PortfolioException;

    /**
     * Updates an existing skill.
     *
     * @param skillId the skill ID
     * @param request the skill data
     * @return the updated skill response
     * @throws PortfolioException if skill not found
     */
    SkillResponseDTO updateSkill(UUID skillId, SkillRequestDTO request) throws PortfolioException;

    /**
     * Deletes a skill.
     *
     * @param skillId the skill ID
     * @throws PortfolioException if skill not found
     */
    void deleteSkill(UUID skillId) throws PortfolioException;
}
