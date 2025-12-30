package com.portfolio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.Contact;

/**
 * Repository for Contact entity operations.
 */
@Repository
public interface IContactRepository extends JpaRepository<Contact, UUID> {

    Optional<Contact> findByProfileId(UUID profileId);
}
