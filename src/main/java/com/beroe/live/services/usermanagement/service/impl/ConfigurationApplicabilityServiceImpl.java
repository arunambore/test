package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.ConfigurationApplicabilityService;
import com.beroe.live.services.usermanagement.domain.ConfigurationApplicability;
import com.beroe.live.services.usermanagement.repository.ConfigurationApplicabilityRepository;
import com.beroe.live.services.usermanagement.service.dto.ConfigurationApplicabilityDTO;
import com.beroe.live.services.usermanagement.service.mapper.ConfigurationApplicabilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ConfigurationApplicability.
 */
@Service
@Transactional
public class ConfigurationApplicabilityServiceImpl implements ConfigurationApplicabilityService {

    private final Logger log = LoggerFactory.getLogger(ConfigurationApplicabilityServiceImpl.class);

    private ConfigurationApplicabilityRepository configurationApplicabilityRepository;

    private ConfigurationApplicabilityMapper configurationApplicabilityMapper;

    public ConfigurationApplicabilityServiceImpl(ConfigurationApplicabilityRepository configurationApplicabilityRepository, ConfigurationApplicabilityMapper configurationApplicabilityMapper) {
        this.configurationApplicabilityRepository = configurationApplicabilityRepository;
        this.configurationApplicabilityMapper = configurationApplicabilityMapper;
    }

    /**
     * Save a configurationApplicability.
     *
     * @param configurationApplicabilityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConfigurationApplicabilityDTO save(ConfigurationApplicabilityDTO configurationApplicabilityDTO) {
        log.debug("Request to save ConfigurationApplicability : {}", configurationApplicabilityDTO);

        ConfigurationApplicability configurationApplicability = configurationApplicabilityMapper.toEntity(configurationApplicabilityDTO);
        configurationApplicability = configurationApplicabilityRepository.save(configurationApplicability);
        return configurationApplicabilityMapper.toDto(configurationApplicability);
    }

    /**
     * Get all the configurationApplicabilities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConfigurationApplicabilityDTO> findAll() {
        log.debug("Request to get all ConfigurationApplicabilities");
        return configurationApplicabilityRepository.findAll().stream()
            .map(configurationApplicabilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one configurationApplicability by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigurationApplicabilityDTO> findOne(Long id) {
        log.debug("Request to get ConfigurationApplicability : {}", id);
        return configurationApplicabilityRepository.findById(id)
            .map(configurationApplicabilityMapper::toDto);
    }

    /**
     * Delete the configurationApplicability by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigurationApplicability : {}", id);
        configurationApplicabilityRepository.deleteById(id);
    }
}
