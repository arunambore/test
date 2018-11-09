package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.RestrictedDomainsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RestrictedDomains.
 */
public interface RestrictedDomainsService {

    /**
     * Save a restrictedDomains.
     *
     * @param restrictedDomainsDTO the entity to save
     * @return the persisted entity
     */
    RestrictedDomainsDTO save(RestrictedDomainsDTO restrictedDomainsDTO);

    /**
     * Get all the restrictedDomains.
     *
     * @return the list of entities
     */
    List<RestrictedDomainsDTO> findAll();


    /**
     * Get the "id" restrictedDomains.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RestrictedDomainsDTO> findOne(Long id);

    /**
     * Delete the "id" restrictedDomains.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
