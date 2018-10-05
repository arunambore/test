package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.UserProfileDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserProfile.
 */
public interface UserProfileService {

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    UserProfileDTO save(UserProfileDTO userProfileDTO);

    /**
     * Get all the userProfiles.
     *
     * @return the list of entities
     */
    List<UserProfileDTO> findAll();
    /**
     * Get all the UserProfileDTO where Invitation is null.
     *
     * @return the list of entities
     */
    List<UserProfileDTO> findAllWhereInvitationIsNull();


    /**
     * Get the "id" userProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserProfileDTO> findOne(Long id);

    /**
     * Delete the "id" userProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
