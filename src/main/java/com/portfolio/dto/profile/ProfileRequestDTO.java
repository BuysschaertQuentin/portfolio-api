package com.portfolio.dto.profile;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 500, message = "Photo URL must not exceed 500 characters")
    private String photoUrl;

    private String bio;
    private String professionalProject;
    private String personalProject;
    private String qualities;
    private String interests;

    @Size(max = 500, message = "CV URL must not exceed 500 characters")
    private String cvUrl;

    private String disponibility;
    private LocalDate disponibilityDate;
}
