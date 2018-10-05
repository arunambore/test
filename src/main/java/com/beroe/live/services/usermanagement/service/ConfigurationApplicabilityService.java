package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.ConfigurationApplicabilityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ConfigurationApplicability.
 */
public interface ConfigurationApplicabilityService {

    /**
     * Save a configurationApplicability.
     *
     * @param configurationApplicabilityDTO the entity to save
     * @return the persisted entity
     */
    ConfigurationApplicabilityDTO save(ConfigurationApplicabilityDTO configurationApplicabilityDTO);

    /**
     * Get all the configurationApplicabilities.
     *
     * @return the list of entities
     */
    List<ConfigurationApplicabilityDTO> findAll();


    /**
     * Get the "id" configurationApplicability.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConfigurationApplicabilityDTO> findOne(Long id);

    /**
     * Delete the "id" configurationApplicability.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
