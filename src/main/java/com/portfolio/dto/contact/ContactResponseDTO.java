package com.portfolio.dto.contact;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for contact response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDTO {

    private UUID id;
    private String email;
    private String phone;
    private String addressCity;
    private String addressCountry;
    private String addressZipCode;
}
