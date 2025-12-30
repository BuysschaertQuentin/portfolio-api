package com.portfolio.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.Media;

/**
 * Repository for Media entity operations.
 */
@Repository
public interface IMediaRepository extends JpaRepository<Media, UUID> {

    List<Media> findByRealizationIdOrderByDisplayOrderAsc(UUID realizationId);

    List<Media> findByRealizationIdAndType(UUID realizationId, String type);

    void deleteByRealizationId(UUID realizationId);
}
