package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.RestrictedDomainsService;
import com.beroe.live.services.usermanagement.domain.RestrictedDomains;
import com.beroe.live.services.usermanagement.repository.RestrictedDomainsRepository;
import com.beroe.live.services.usermanagement.service.dto.RestrictedDomainsDTO;
import com.beroe.live.services.usermanagement.service.mapper.RestrictedDomainsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RestrictedDomains.
 */
@Service
@Transactional
public class RestrictedDomainsServiceImpl implements RestrictedDomainsService {

    private final Logger log = LoggerFactory.getLogger(RestrictedDomainsServiceImpl.class);

    private final RestrictedDomainsRepository restrictedDomainsRepository;

    private final RestrictedDomainsMapper restrictedDomainsMapper;

    public RestrictedDomainsServiceImpl(RestrictedDomainsRepository restrictedDomainsRepository, RestrictedDomainsMapper restrictedDomainsMapper) {
        this.restrictedDomainsRepository = restrictedDomainsRepository;
        this.restrictedDomainsMapper = restrictedDomainsMapper;
    }

    /**
     * Save a restrictedDomains.
     *
     * @param restrictedDomainsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RestrictedDomainsDTO save(RestrictedDomainsDTO restrictedDomainsDTO) {
        log.debug("Request to save RestrictedDomains : {}", restrictedDomainsDTO);

        RestrictedDomains restrictedDomains = restrictedDomainsMapper.toEntity(restrictedDomainsDTO);
        restrictedDomains = restrictedDomainsRepository.save(restrictedDomains);
        return restrictedDomainsMapper.toDto(restrictedDomains);
    }

    /**
     * Get all the restrictedDomains.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RestrictedDomainsDTO> findAll() {
        log.debug("Request to get all RestrictedDomains");
        return restrictedDomainsRepository.findAll().stream()
            .map(restrictedDomainsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one restrictedDomains by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RestrictedDomainsDTO> findOne(Long id) {
        log.debug("Request to get RestrictedDomains : {}", id);
        return restrictedDomainsRepository.findById(id)
            .map(restrictedDomainsMapper::toDto);
    }

    /**
     * Delete the restrictedDomains by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RestrictedDomains : {}", id);
        restrictedDomainsRepository.deleteById(id);
    }
}
