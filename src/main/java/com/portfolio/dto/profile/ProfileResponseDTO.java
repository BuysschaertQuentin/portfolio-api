package com.portfolio.dto.profile;

import com.portfolio.dto.contact.ContactResponseDTO;
import com.portfolio.dto.sociallink.SocialLinkResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO for profile response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String title;
    private String photoUrl;
    private String bio;
    private String professionalProject;
    private String personalProject;
    private String qualities;
    private String interests;
    private String cvUrl;
    private String disponibility;
    private LocalDate disponibilityDate;
    private Instant updatedAt;
    private ContactResponseDTO contact;
    private List<SocialLinkResponseDTO> socialLinks;
}
