package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * RealizationSkill entity - Junction table for Realization <-> Skill many-to-many.
 */
@Entity
@Table(name = "realization_skill", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"realization_id", "skill_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class RealizationSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realization_id", nullable = false)
    private Realization realization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
