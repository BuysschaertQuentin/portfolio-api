package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Skill entity for technical and soft skills.
 */
@Entity
@Table(name = "skill")
@Getter
@Setter
@NoArgsConstructor
public class Skill extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SkillCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "proficiency_level")
    private Integer proficiencyLevel;

    @Column(columnDefinition = "TEXT")
    private String definition;

    @Column(name = "professional_context", columnDefinition = "TEXT")
    private String professionalContext;

    @Column(name = "self_criticism", columnDefinition = "TEXT")
    private String selfCriticism;

    @Column(name = "is_highlighted")
    private Boolean isHighlighted = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkill> experienceSkills = new ArrayList<>();

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RealizationSkill> realizationSkills = new ArrayList<>();
}
