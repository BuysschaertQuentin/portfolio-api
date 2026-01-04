package com.portfolio.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.datamapper.ProfileMapper;
import com.portfolio.dto.profile.ProfileRequestDTO;
import com.portfolio.dto.profile.ProfileResponseDTO;
import com.portfolio.entity.Profile;
import com.portfolio.entity.User;
import com.portfolio.exception.PortfolioException;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.IProfileRepository;
import com.portfolio.repository.IUserRepository;
import com.portfolio.service.IProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of IProfileService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private final IProfileRepository profileRepository;
    private final IUserRepository userRepository;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileByUserId(UUID userId) throws PortfolioException {
        log.debug("Getting profile for user ID: {}", userId);

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "userId", userId));

        return profileMapper.toResponseDTO(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDTO getMainProfile() throws PortfolioException {
        log.debug("Getting main profile");

        Profile profile = profileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No profile exists in the system"));

        return profileMapper.toResponseDTO(profile);
    }

    @Override
    @Transactional
    public ProfileResponseDTO saveProfile(UUID userId, ProfileRequestDTO request) throws PortfolioException {
        log.debug("Saving profile for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Profile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Profile newProfile = new Profile();
                    newProfile.setUser(user);
                    return newProfile;
                });

        updateProfileFromRequest(profile, request);

        Profile savedProfile = profileRepository.save(profile);
        log.info("Profile saved for user ID: {}", userId);

        return profileMapper.toResponseDTO(savedProfile);
    }

    @Override
    @Transactional
    public ProfileResponseDTO updateProfile(UUID profileId, ProfileRequestDTO request) throws PortfolioException {
        log.debug("Updating profile ID: {}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", profileId));

        updateProfileFromRequest(profile, request);

        Profile savedProfile = profileRepository.save(profile);
        log.info("Profile updated: {}", profileId);

        return profileMapper.toResponseDTO(savedProfile);
    }

    private void updateProfileFromRequest(Profile profile, ProfileRequestDTO request) {
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setTitle(request.getTitle());
        profile.setPhotoUrl(request.getPhotoUrl());
        profile.setBio(request.getBio());
        profile.setProfessionalProject(request.getProfessionalProject());
        profile.setPersonalProject(request.getPersonalProject());
        profile.setQualities(request.getQualities());
        profile.setInterests(request.getInterests());
        profile.setCvUrl(request.getCvUrl());
        profile.setDisponibility(request.getDisponibility());
        profile.setDisponibilityDate(request.getDisponibilityDate());
        profile.setUpdatedAt(Instant.now());
    }
}
