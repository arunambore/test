package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.UserStateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserState.
 */
public interface UserStateService {

    /**
     * Save a userState.
     *
     * @param userStateDTO the entity to save
     * @return the persisted entity
     */
    UserStateDTO save(UserStateDTO userStateDTO);

    /**
     * Get all the userStates.
     *
     * @return the list of entities
     */
    List<UserStateDTO> findAll();


    /**
     * Get the "id" userState.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserStateDTO> findOne(Long id);

    /**
     * Delete the "id" userState.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
