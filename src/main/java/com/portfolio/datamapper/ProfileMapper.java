package com.portfolio.datamapper;

import com.portfolio.dto.profile.ProfileResponseDTO;
import com.portfolio.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper for Profile entity <-> DTO conversions.
 */
@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final ContactMapper contactMapper;
    private final SocialLinkMapper socialLinkMapper;

    /**
     * Converts Profile entity to ProfileResponseDTO.
     *
     * @param profile the profile entity
     * @return the profile response DTO
     */
    public ProfileResponseDTO toResponseDTO(Profile profile) {
        if (profile == null) {
            return null;
        }

        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .title(profile.getTitle())
                .photoUrl(profile.getPhotoUrl())
                .bio(profile.getBio())
                .professionalProject(profile.getProfessionalProject())
                .personalProject(profile.getPersonalProject())
                .qualities(profile.getQualities())
                .interests(profile.getInterests())
                .cvUrl(profile.getCvUrl())
                .disponibility(profile.getDisponibility())
                .disponibilityDate(profile.getDisponibilityDate())
                .updatedAt(profile.getUpdatedAt())
                .contact(contactMapper.toResponseDTO(profile.getContact()))
                .socialLinks(profile.getSocialLinks() != null
                        ? profile.getSocialLinks().stream()
                            .map(socialLinkMapper::toResponseDTO)
                            .toList()
                        : null)
                .build();
    }
}
