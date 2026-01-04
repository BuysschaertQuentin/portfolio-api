package com.portfolio.service;

import java.util.List;
import java.util.UUID;

import com.portfolio.dto.realization.RealizationRequestDTO;
import com.portfolio.dto.realization.RealizationResponseDTO;
import com.portfolio.exception.PortfolioException;

/**
 * Service interface for Realization operations.
 */
public interface IRealizationService {

    /**
     * Gets all realizations (admin).
     *
     * @return list of all realizations
     */
    List<RealizationResponseDTO> getAllRealizations();

    /**
     * Gets published realizations only (public).
     *
     * @return list of published realizations
     */
    List<RealizationResponseDTO> getPublishedRealizations();

    /**
     * Gets featured published realizations.
     *
     * @return list of featured realizations
     */
    List<RealizationResponseDTO> getFeaturedRealizations();

    /**
     * Gets realizations by skill.
     *
     * @param skillId the skill ID
     * @return list of realizations using this skill
     */
    List<RealizationResponseDTO> getRealizationsBySkill(UUID skillId);

    /**
     * Gets a realization by ID.
     *
     * @param realizationId the realization ID
     * @return the realization response
     * @throws PortfolioException if realization not found
     */
    RealizationResponseDTO getRealizationById(UUID realizationId) throws PortfolioException;

    /**
     * Gets a realization by slug (public).
     *
     * @param slug the realization slug
     * @return the realization response
     * @throws PortfolioException if realization not found
     */
    RealizationResponseDTO getRealizationBySlug(String slug) throws PortfolioException;

    /**
     * Creates a new realization.
     *
     * @param request the realization data
     * @return the created realization response
     * @throws PortfolioException if operation fails
     */
    RealizationResponseDTO createRealization(RealizationRequestDTO request) throws PortfolioException;

    /**
     * Updates an existing realization.
     *
     * @param realizationId the realization ID
     * @param request       the realization data
     * @return the updated realization response
     * @throws PortfolioException if realization not found
     */
    RealizationResponseDTO updateRealization(UUID realizationId, RealizationRequestDTO request) throws PortfolioException;

    /**
     * Deletes a realization.
     *
     * @param realizationId the realization ID
     * @throws PortfolioException if realization not found
     */
    void deleteRealization(UUID realizationId) throws PortfolioException;
}
