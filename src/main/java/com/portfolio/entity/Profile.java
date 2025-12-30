package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Profile entity containing personal and professional information.
 */
@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String title;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "professional_project", columnDefinition = "TEXT")
    private String professionalProject;

    @Column(name = "personal_project", columnDefinition = "TEXT")
    private String personalProject;

    @Column(columnDefinition = "TEXT")
    private String qualities;

    @Column(columnDefinition = "TEXT")
    private String interests;

    @Column(name = "cv_url")
    private String cvUrl;

    @Column
    private String disponibility;

    @Column(name = "disponibility_date")
    private LocalDate disponibilityDate;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contact contact;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<SocialLink> socialLinks = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
