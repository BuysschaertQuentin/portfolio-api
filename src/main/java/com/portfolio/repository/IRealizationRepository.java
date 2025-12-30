package com.portfolio.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.Realization;

/**
 * Repository for Realization entity operations.
 */
@Repository
public interface IRealizationRepository extends JpaRepository<Realization, UUID> {

    Optional<Realization> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Realization> findByStatusOrderByDisplayOrderAsc(String status);

    List<Realization> findByIsFeaturedTrueAndStatusOrderByDisplayOrderAsc(String status);

    List<Realization> findByExperienceIdOrderByDisplayOrderAsc(UUID experienceId);

    List<Realization> findAllByOrderByDisplayOrderAsc();

    @Query("SELECT r FROM Realization r WHERE r.status = 'published' ORDER BY r.displayOrder ASC")
    List<Realization> findAllPublished();

    @Query("SELECT r FROM Realization r WHERE r.isFeatured = true AND r.status = 'published' ORDER BY r.displayOrder ASC")
    List<Realization> findFeaturedPublished();

    @Query("SELECT r FROM Realization r JOIN r.realizationSkills rs WHERE rs.skill.id = :skillId AND r.status = 'published' ORDER BY r.displayOrder ASC")
    List<Realization> findPublishedBySkillId(@Param("skillId") UUID skillId);
}
