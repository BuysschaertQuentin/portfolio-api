package com.portfolio.service;

import java.util.List;
import java.util.UUID;

import com.portfolio.dto.experience.ExperienceRequestDTO;
import com.portfolio.dto.experience.ExperienceResponseDTO;
import com.portfolio.exception.PortfolioException;

/**
 * Service interface for Experience operations.
 */
public interface IExperienceService {

    /**
     * Gets all experiences.
     *
     * @return list of experiences
     */
    List<ExperienceResponseDTO> getAllExperiences();

    /**
     * Gets visible experiences only.
     *
     * @return list of visible experiences
     */
    List<ExperienceResponseDTO> getVisibleExperiences();

    /**
     * Gets experiences by type.
     *
     * @param type the experience type
     * @return list of experiences of the specified type
     */
    List<ExperienceResponseDTO> getExperiencesByType(String type);

    /**
     * Gets an experience by ID.
     *
     * @param experienceId the experience ID
     * @return the experience response
     * @throws PortfolioException if experience not found
     */
    ExperienceResponseDTO getExperienceById(UUID experienceId) throws PortfolioException;

    /**
     * Creates a new experience.
     *
     * @param request the experience data
     * @return the created experience response
     * @throws PortfolioException if operation fails
     */
    ExperienceResponseDTO createExperience(ExperienceRequestDTO request) throws PortfolioException;

    /**
     * Updates an existing experience.
     *
     * @param experienceId the experience ID
     * @param request      the experience data
     * @return the updated experience response
     * @throws PortfolioException if experience not found
     */
    ExperienceResponseDTO updateExperience(UUID experienceId, ExperienceRequestDTO request) throws PortfolioException;

    /**
     * Deletes an experience.
     *
     * @param experienceId the experience ID
     * @throws PortfolioException if experience not found
     */
    void deleteExperience(UUID experienceId) throws PortfolioException;
}
