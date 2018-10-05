package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.SupplierInvitationDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SupplierInvitationDetails.
 */
public interface SupplierInvitationDetailsService {

    /**
     * Save a supplierInvitationDetails.
     *
     * @param supplierInvitationDetailsDTO the entity to save
     * @return the persisted entity
     */
    SupplierInvitationDetailsDTO save(SupplierInvitationDetailsDTO supplierInvitationDetailsDTO);

    /**
     * Get all the supplierInvitationDetails.
     *
     * @return the list of entities
     */
    List<SupplierInvitationDetailsDTO> findAll();
    /**
     * Get all the SupplierInvitationDetailsDTO where Invitation is null.
     *
     * @return the list of entities
     */
    List<SupplierInvitationDetailsDTO> findAllWhereInvitationIsNull();


    /**
     * Get the "id" supplierInvitationDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplierInvitationDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" supplierInvitationDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
