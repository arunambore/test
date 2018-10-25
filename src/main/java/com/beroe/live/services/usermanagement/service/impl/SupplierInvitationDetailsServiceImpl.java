package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.SupplierInvitationDetailsService;
import com.beroe.live.services.usermanagement.domain.SupplierInvitationDetails;
import com.beroe.live.services.usermanagement.repository.SupplierInvitationDetailsRepository;
import com.beroe.live.services.usermanagement.service.dto.SupplierInvitationDetailsDTO;
import com.beroe.live.services.usermanagement.service.mapper.SupplierInvitationDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing SupplierInvitationDetails.
 */
@Service
@Transactional
public class SupplierInvitationDetailsServiceImpl implements SupplierInvitationDetailsService {

    private final Logger log = LoggerFactory.getLogger(SupplierInvitationDetailsServiceImpl.class);

    private SupplierInvitationDetailsRepository supplierInvitationDetailsRepository;

    private SupplierInvitationDetailsMapper supplierInvitationDetailsMapper;

    public SupplierInvitationDetailsServiceImpl(SupplierInvitationDetailsRepository supplierInvitationDetailsRepository, SupplierInvitationDetailsMapper supplierInvitationDetailsMapper) {
        this.supplierInvitationDetailsRepository = supplierInvitationDetailsRepository;
        this.supplierInvitationDetailsMapper = supplierInvitationDetailsMapper;
    }

    /**
     * Save a supplierInvitationDetails.
     *
     * @param supplierInvitationDetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SupplierInvitationDetailsDTO save(SupplierInvitationDetailsDTO supplierInvitationDetailsDTO) {
        log.debug("Request to save SupplierInvitationDetails : {}", supplierInvitationDetailsDTO);

        SupplierInvitationDetails supplierInvitationDetails = supplierInvitationDetailsMapper.toEntity(supplierInvitationDetailsDTO);
        supplierInvitationDetails = supplierInvitationDetailsRepository.save(supplierInvitationDetails);
        return supplierInvitationDetailsMapper.toDto(supplierInvitationDetails);
    }

    /**
     * Get all the supplierInvitationDetails.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierInvitationDetailsDTO> findAll() {
        log.debug("Request to get all SupplierInvitationDetails");
        return supplierInvitationDetailsRepository.findAll().stream()
            .map(supplierInvitationDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the supplierInvitationDetails where Invitation is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SupplierInvitationDetailsDTO> findAllWhereInvitationIsNull() {
        log.debug("Request to get all supplierInvitationDetails where Invitation is null");
        return StreamSupport
            .stream(supplierInvitationDetailsRepository.findAll().spliterator(), false)
            .filter(supplierInvitationDetails -> supplierInvitationDetails.getInvitation() == null)
            .map(supplierInvitationDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one supplierInvitationDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierInvitationDetailsDTO> findOne(Long id) {
        log.debug("Request to get SupplierInvitationDetails : {}", id);
        return supplierInvitationDetailsRepository.findById(id)
            .map(supplierInvitationDetailsMapper::toDto);
    }

    /**
     * Delete the supplierInvitationDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierInvitationDetails : {}", id);
        supplierInvitationDetailsRepository.deleteById(id);
    }
}
