package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * ExperienceSkill entity - Junction table for Experience <-> Skill many-to-many.
 */
@Entity
@Table(name = "experience_skill", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"experience_id", "skill_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class ExperienceSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
