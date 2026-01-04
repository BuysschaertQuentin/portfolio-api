package com.portfolio.dto.sociallink;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a social link.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLinkRequestDTO {

    @NotBlank(message = "Platform is required")
    @Size(max = 50, message = "Platform must not exceed 50 characters")
    private String platform;

    @NotBlank(message = "URL is required")
    @URL(message = "Invalid URL format")
    @Size(max = 500, message = "URL must not exceed 500 characters")
    private String url;

    private Integer displayOrder;
}
