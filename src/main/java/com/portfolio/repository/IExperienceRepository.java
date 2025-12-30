package com.portfolio.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.Experience;

/**
 * Repository for Experience entity operations.
 */
@Repository
public interface IExperienceRepository extends JpaRepository<Experience, UUID> {

    List<Experience> findByTypeOrderByDisplayOrderAsc(String type);

    List<Experience> findByIsVisibleTrueOrderByDisplayOrderAsc();

    List<Experience> findByTypeAndIsVisibleTrueOrderByDisplayOrderAsc(String type);

    List<Experience> findAllByOrderByDisplayOrderAsc();
}
