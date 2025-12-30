package com.portfolio.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.SocialLink;

/**
 * Repository for SocialLink entity operations.
 */
@Repository
public interface ISocialLinkRepository extends JpaRepository<SocialLink, UUID> {

    List<SocialLink> findByProfileIdOrderByDisplayOrderAsc(UUID profileId);

    void deleteByProfileId(UUID profileId);
}
