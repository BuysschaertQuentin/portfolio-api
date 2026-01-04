package com.portfolio.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.datamapper.MediaMapper;
import com.portfolio.dto.media.MediaRequestDTO;
import com.portfolio.dto.media.MediaResponseDTO;
import com.portfolio.entity.Media;
import com.portfolio.entity.Realization;
import com.portfolio.exception.PortfolioException;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.IMediaRepository;
import com.portfolio.repository.IRealizationRepository;
import com.portfolio.service.IMediaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of IMediaService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements IMediaService {

    private final IMediaRepository mediaRepository;
    private final IRealizationRepository realizationRepository;
    private final MediaMapper mediaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponseDTO> getMediasByRealization(UUID realizationId) {
        log.debug("Getting medias for realization: {}", realizationId);
        return mediaRepository.findByRealizationIdOrderByDisplayOrderAsc(realizationId).stream()
                .map(mediaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MediaResponseDTO getMediaById(UUID mediaId) throws PortfolioException {
        log.debug("Getting media by ID: {}", mediaId);

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media", "id", mediaId));

        return mediaMapper.toResponseDTO(media);
    }

    @Override
    @Transactional
    public MediaResponseDTO createMedia(MediaRequestDTO request) throws PortfolioException {
        log.debug("Creating media for realization: {}", request.getRealizationId());

        Realization realization = realizationRepository.findById(request.getRealizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Realization", "id", request.getRealizationId()));

        Media media = new Media();
        media.setRealization(realization);
        updateMediaFromRequest(media, request);

        Media savedMedia = mediaRepository.save(media);
        log.info("Media created: {}", savedMedia.getId());

        return mediaMapper.toResponseDTO(savedMedia);
    }

    @Override
    @Transactional
    public MediaResponseDTO updateMedia(UUID mediaId, MediaRequestDTO request) throws PortfolioException {
        log.debug("Updating media: {}", mediaId);

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media", "id", mediaId));

        updateMediaFromRequest(media, request);

        Media savedMedia = mediaRepository.save(media);
        log.info("Media updated: {}", mediaId);

        return mediaMapper.toResponseDTO(savedMedia);
    }

    @Override
    @Transactional
    public void deleteMedia(UUID mediaId) throws PortfolioException {
        log.debug("Deleting media: {}", mediaId);

        if (!mediaRepository.existsById(mediaId)) {
            throw new ResourceNotFoundException("Media", "id", mediaId);
        }

        mediaRepository.deleteById(mediaId);
        log.info("Media deleted: {}", mediaId);
    }

    private void updateMediaFromRequest(Media media, MediaRequestDTO request) {
        media.setType(request.getType());
        media.setUrl(request.getUrl());
        media.setAltText(request.getAltText());
        media.setCaption(request.getCaption());
        media.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
    }
}
