package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.CompanyDomainService;
import com.beroe.live.services.usermanagement.domain.CompanyDomain;
import com.beroe.live.services.usermanagement.repository.CompanyDomainRepository;
import com.beroe.live.services.usermanagement.service.dto.CompanyDomainDTO;
import com.beroe.live.services.usermanagement.service.mapper.CompanyDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CompanyDomain.
 */
@Service
@Transactional
public class CompanyDomainServiceImpl implements CompanyDomainService {

    private final Logger log = LoggerFactory.getLogger(CompanyDomainServiceImpl.class);

    private final CompanyDomainRepository companyDomainRepository;

    private final CompanyDomainMapper companyDomainMapper;

    public CompanyDomainServiceImpl(CompanyDomainRepository companyDomainRepository, CompanyDomainMapper companyDomainMapper) {
        this.companyDomainRepository = companyDomainRepository;
        this.companyDomainMapper = companyDomainMapper;
    }

    /**
     * Save a companyDomain.
     *
     * @param companyDomainDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyDomainDTO save(CompanyDomainDTO companyDomainDTO) {
        log.debug("Request to save CompanyDomain : {}", companyDomainDTO);

        CompanyDomain companyDomain = companyDomainMapper.toEntity(companyDomainDTO);
        companyDomain = companyDomainRepository.save(companyDomain);
        return companyDomainMapper.toDto(companyDomain);
    }

    /**
     * Get all the companyDomains.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompanyDomainDTO> findAll() {
        log.debug("Request to get all CompanyDomains");
        return companyDomainRepository.findAll().stream()
            .map(companyDomainMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one companyDomain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyDomainDTO> findOne(Long id) {
        log.debug("Request to get CompanyDomain : {}", id);
        return companyDomainRepository.findById(id)
            .map(companyDomainMapper::toDto);
    }

    /**
     * Delete the companyDomain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyDomain : {}", id);
        companyDomainRepository.deleteById(id);
    }
}
