package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.SystemConfigurationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SystemConfiguration.
 */
public interface SystemConfigurationService {

    /**
     * Save a systemConfiguration.
     *
     * @param systemConfigurationDTO the entity to save
     * @return the persisted entity
     */
    SystemConfigurationDTO save(SystemConfigurationDTO systemConfigurationDTO);

    /**
     * Get all the systemConfigurations.
     *
     * @return the list of entities
     */
    List<SystemConfigurationDTO> findAll();


    /**
     * Get the "id" systemConfiguration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SystemConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" systemConfiguration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
