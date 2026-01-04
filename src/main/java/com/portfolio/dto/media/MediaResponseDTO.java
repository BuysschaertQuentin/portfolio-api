package com.portfolio.dto.media;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for media response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponseDTO {

    private UUID id;
    private UUID realizationId;
    private String type;
    private String url;
    private String altText;
    private String caption;
    private Integer displayOrder;
}
