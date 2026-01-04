package com.portfolio.service;

import java.util.List;
import java.util.UUID;

import com.portfolio.dto.media.MediaRequestDTO;
import com.portfolio.dto.media.MediaResponseDTO;
import com.portfolio.exception.PortfolioException;

/**
 * Service interface for Media operations.
 */
public interface IMediaService {

    /**
     * Gets all medias for a realization.
     *
     * @param realizationId the realization ID
     * @return list of medias
     */
    List<MediaResponseDTO> getMediasByRealization(UUID realizationId);

    /**
     * Gets a media by ID.
     *
     * @param mediaId the media ID
     * @return the media response
     * @throws PortfolioException if media not found
     */
    MediaResponseDTO getMediaById(UUID mediaId) throws PortfolioException;

    /**
     * Creates a new media for a realization.
     *
     * @param request the media data
     * @return the created media response
     * @throws PortfolioException if realization not found
     */
    MediaResponseDTO createMedia(MediaRequestDTO request) throws PortfolioException;

    /**
     * Updates an existing media.
     *
     * @param mediaId the media ID
     * @param request the media data
     * @return the updated media response
     * @throws PortfolioException if media not found
     */
    MediaResponseDTO updateMedia(UUID mediaId, MediaRequestDTO request) throws PortfolioException;

    /**
     * Deletes a media.
     *
     * @param mediaId the media ID
     * @throws PortfolioException if media not found
     */
    void deleteMedia(UUID mediaId) throws PortfolioException;
}
