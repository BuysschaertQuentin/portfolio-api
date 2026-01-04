package com.portfolio.dto.media;

import java.util.UUID;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a media.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequestDTO {

    private UUID realizationId;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "URL is required")
    @URL(message = "Invalid URL format")
    @Size(max = 500, message = "URL must not exceed 500 characters")
    private String url;

    @Size(max = 500, message = "Alt text must not exceed 500 characters")
    private String altText;

    private String caption;
    private Integer displayOrder;
}
