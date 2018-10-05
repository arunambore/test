package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.InvitationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Invitation.
 */
public interface InvitationService {

    /**
     * Save a invitation.
     *
     * @param invitationDTO the entity to save
     * @return the persisted entity
     */
    InvitationDTO save(InvitationDTO invitationDTO);

    /**
     * Get all the invitations.
     *
     * @return the list of entities
     */
    List<InvitationDTO> findAll();


    /**
     * Get the "id" invitation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InvitationDTO> findOne(Long id);

    /**
     * Delete the "id" invitation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
