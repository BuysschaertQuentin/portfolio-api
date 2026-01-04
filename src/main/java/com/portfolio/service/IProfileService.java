package com.portfolio.service;

import java.util.UUID;

import com.portfolio.dto.profile.ProfileRequestDTO;
import com.portfolio.dto.profile.ProfileResponseDTO;
import com.portfolio.exception.PortfolioException;

/**
 * Service interface for Profile operations.
 */
public interface IProfileService {

    /**
     * Gets the profile by user ID.
     *
     * @param userId the user ID
     * @return the profile response
     * @throws PortfolioException if profile not found
     */
    ProfileResponseDTO getProfileByUserId(UUID userId) throws PortfolioException;

    /**
     * Gets the main profile (first profile in the system).
     *
     * @return the profile response
     * @throws PortfolioException if no profile exists
     */
    ProfileResponseDTO getMainProfile() throws PortfolioException;

    /**
     * Creates or updates the profile for a user.
     *
     * @param userId  the user ID
     * @param request the profile data
     * @return the created/updated profile response
     * @throws PortfolioException if operation fails
     */
    ProfileResponseDTO saveProfile(UUID userId, ProfileRequestDTO request) throws PortfolioException;

    /**
     * Updates an existing profile.
     *
     * @param profileId the profile ID
     * @param request   the profile data
     * @return the updated profile response
     * @throws PortfolioException if profile not found
     */
    ProfileResponseDTO updateProfile(UUID profileId, ProfileRequestDTO request) throws PortfolioException;
}
