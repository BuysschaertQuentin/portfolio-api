package com.portfolio.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.RealizationSkill;

/**
 * Repository for RealizationSkill junction entity operations.
 */
@Repository
public interface IRealizationSkillRepository extends JpaRepository<RealizationSkill, UUID> {

    List<RealizationSkill> findByRealizationId(UUID realizationId);

    List<RealizationSkill> findBySkillId(UUID skillId);

    void deleteByRealizationId(UUID realizationId);

    boolean existsByRealizationIdAndSkillId(UUID realizationId, UUID skillId);
}
