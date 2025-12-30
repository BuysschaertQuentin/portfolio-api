package com.portfolio.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.ExperienceSkill;

/**
 * Repository for ExperienceSkill junction entity operations.
 */
@Repository
public interface IExperienceSkillRepository extends JpaRepository<ExperienceSkill, UUID> {

    List<ExperienceSkill> findByExperienceId(UUID experienceId);

    List<ExperienceSkill> findBySkillId(UUID skillId);

    void deleteByExperienceId(UUID experienceId);

    boolean existsByExperienceIdAndSkillId(UUID experienceId, UUID skillId);
}
