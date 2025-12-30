package com.portfolio.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.SkillCategory;

/**
 * Repository for SkillCategory entity operations.
 */
@Repository
public interface ISkillCategoryRepository extends JpaRepository<SkillCategory, UUID> {

    List<SkillCategory> findAllByOrderByDisplayOrderAsc();

    Optional<SkillCategory> findByName(String name);

    boolean existsByName(String name);
}
