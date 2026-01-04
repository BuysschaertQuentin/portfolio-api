package com.portfolio.datamapper;

import org.springframework.stereotype.Component;

import com.portfolio.dto.sociallink.SocialLinkResponseDTO;
import com.portfolio.entity.SocialLink;

/**
 * Mapper for SocialLink entity <-> DTO conversions.
 */
@Component
public class SocialLinkMapper {

    /**
     * Converts SocialLink entity to SocialLinkResponseDTO.
     *
     * @param socialLink the social link entity
     * @return the social link response DTO
     */
    public SocialLinkResponseDTO toResponseDTO(SocialLink socialLink) {
        if (socialLink == null) {
            return null;
        }

        return SocialLinkResponseDTO.builder()
                .id(socialLink.getId())
                .platform(socialLink.getPlatform())
                .url(socialLink.getUrl())
                .displayOrder(socialLink.getDisplayOrder())
                .build();
    }
}
