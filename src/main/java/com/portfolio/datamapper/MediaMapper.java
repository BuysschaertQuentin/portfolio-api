package com.portfolio.datamapper;

import org.springframework.stereotype.Component;

import com.portfolio.dto.media.MediaResponseDTO;
import com.portfolio.entity.Media;

/**
 * Mapper for Media entity <-> DTO conversions.
 */
@Component
public class MediaMapper {

    /**
     * Converts Media entity to MediaResponseDTO.
     *
     * @param media the media entity
     * @return the media response DTO
     */
    public MediaResponseDTO toResponseDTO(Media media) {
        if (media == null) {
            return null;
        }

        return MediaResponseDTO.builder()
                .id(media.getId())
                .realizationId(media.getRealization() != null ? media.getRealization().getId() : null)
                .type(media.getType())
                .url(media.getUrl())
                .altText(media.getAltText())
                .caption(media.getCaption())
                .displayOrder(media.getDisplayOrder())
                .build();
    }
}
