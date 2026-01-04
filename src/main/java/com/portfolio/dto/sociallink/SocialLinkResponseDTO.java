package com.portfolio.dto.sociallink;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for social link response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLinkResponseDTO {

    private UUID id;
    private String platform;
    private String url;
    private Integer displayOrder;
}
