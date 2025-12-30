package com.portfolio.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.Skill;

/**
 * Repository for Skill entity operations.
 */
@Repository
public interface ISkillRepository extends JpaRepository<Skill, UUID> {

    List<Skill> findByCategoryIdOrderByDisplayOrderAsc(UUID categoryId);

    List<Skill> findByTypeOrderByDisplayOrderAsc(String type);

    List<Skill> findByIsHighlightedTrueOrderByDisplayOrderAsc();

    @Query("SELECT s FROM Skill s WHERE s.category.id = :categoryId AND s.isHighlighted = true ORDER BY s.displayOrder ASC")
    List<Skill> findHighlightedByCategoryId(@Param("categoryId") UUID categoryId);

    boolean existsByNameAndCategoryId(String name, UUID categoryId);
}
