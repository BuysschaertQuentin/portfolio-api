package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Experience entity for jobs, internships, training, certifications.
 */
@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
public class Experience extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String type;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent = false;

    @Column(nullable = false)
    private String title;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "organization_logo_url")
    private String organizationLogoUrl;

    @Column(name = "organization_url")
    private String organizationUrl;

    @Column
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String missions;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    @Column(name = "is_visible")
    private Boolean isVisible = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkill> experienceSkills = new ArrayList<>();

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
    @OrderBy("displayOrder ASC")
    private List<Realization> realizations = new ArrayList<>();
}
