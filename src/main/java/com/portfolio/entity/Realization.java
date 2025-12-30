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
 * Realization entity for projects and achievements.
 */
@Entity
@Table(name = "realization")
@Getter
@Setter
@NoArgsConstructor
public class Realization extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "full_presentation", columnDefinition = "TEXT")
    private String fullPresentation;

    @Column(columnDefinition = "TEXT")
    private String objectives;

    @Column(columnDefinition = "TEXT")
    private String context;

    @Column(columnDefinition = "TEXT")
    private String stakes;

    @Column(columnDefinition = "TEXT")
    private String steps;

    @Column(name = "actors_interactions", columnDefinition = "TEXT")
    private String actorsInteractions;

    @Column(columnDefinition = "TEXT")
    private String results;

    @Column(name = "next_steps", columnDefinition = "TEXT")
    private String nextSteps;

    @Column(name = "critical_view", columnDefinition = "TEXT")
    private String criticalView;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "demo_url")
    private String demoUrl;

    @Column(name = "repo_url")
    private String repoUrl;

    @Column(nullable = false)
    private String status = "draft";

    @Column(name = "project_date")
    private LocalDate projectDate;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;

    @OneToMany(mappedBy = "realization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RealizationSkill> realizationSkills = new ArrayList<>();

    @OneToMany(mappedBy = "realization", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<Media> medias = new ArrayList<>();
}
