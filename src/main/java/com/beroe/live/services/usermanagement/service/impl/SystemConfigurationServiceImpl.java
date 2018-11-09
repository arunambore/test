package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.SystemConfigurationService;
import com.beroe.live.services.usermanagement.domain.SystemConfiguration;
import com.beroe.live.services.usermanagement.repository.SystemConfigurationRepository;
import com.beroe.live.services.usermanagement.service.dto.SystemConfigurationDTO;
import com.beroe.live.services.usermanagement.service.mapper.SystemConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SystemConfiguration.
 */
@Service
@Transactional
public class SystemConfigurationServiceImpl implements SystemConfigurationService {

    private final Logger log = LoggerFactory.getLogger(SystemConfigurationServiceImpl.class);

    private final SystemConfigurationRepository systemConfigurationRepository;

    private final SystemConfigurationMapper systemConfigurationMapper;

    public SystemConfigurationServiceImpl(SystemConfigurationRepository systemConfigurationRepository, SystemConfigurationMapper systemConfigurationMapper) {
        this.systemConfigurationRepository = systemConfigurationRepository;
        this.systemConfigurationMapper = systemConfigurationMapper;
    }

    /**
     * Save a systemConfiguration.
     *
     * @param systemConfigurationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SystemConfigurationDTO save(SystemConfigurationDTO systemConfigurationDTO) {
        log.debug("Request to save SystemConfiguration : {}", systemConfigurationDTO);

        SystemConfiguration systemConfiguration = systemConfigurationMapper.toEntity(systemConfigurationDTO);
        systemConfiguration = systemConfigurationRepository.save(systemConfiguration);
        return systemConfigurationMapper.toDto(systemConfiguration);
    }

    /**
     * Get all the systemConfigurations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemConfigurationDTO> findAll() {
        log.debug("Request to get all SystemConfigurations");
        return systemConfigurationRepository.findAll().stream()
            .map(systemConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one systemConfiguration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SystemConfigurationDTO> findOne(Long id) {
        log.debug("Request to get SystemConfiguration : {}", id);
        return systemConfigurationRepository.findById(id)
            .map(systemConfigurationMapper::toDto);
    }

    /**
     * Delete the systemConfiguration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemConfiguration : {}", id);
        systemConfigurationRepository.deleteById(id);
    }
}
