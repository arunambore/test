package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.InvitationService;
import com.beroe.live.services.usermanagement.domain.Invitation;
import com.beroe.live.services.usermanagement.repository.InvitationRepository;
import com.beroe.live.services.usermanagement.service.dto.InvitationDTO;
import com.beroe.live.services.usermanagement.service.mapper.InvitationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Invitation.
 */
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final Logger log = LoggerFactory.getLogger(InvitationServiceImpl.class);

    private InvitationRepository invitationRepository;

    private InvitationMapper invitationMapper;

    public InvitationServiceImpl(InvitationRepository invitationRepository, InvitationMapper invitationMapper) {
        this.invitationRepository = invitationRepository;
        this.invitationMapper = invitationMapper;
    }

    /**
     * Save a invitation.
     *
     * @param invitationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InvitationDTO save(InvitationDTO invitationDTO) {
        log.debug("Request to save Invitation : {}", invitationDTO);

        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        invitation = invitationRepository.save(invitation);
        return invitationMapper.toDto(invitation);
    }

    /**
     * Get all the invitations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvitationDTO> findAll() {
        log.debug("Request to get all Invitations");
        return invitationRepository.findAll().stream()
            .map(invitationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one invitation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvitationDTO> findOne(Long id) {
        log.debug("Request to get Invitation : {}", id);
        return invitationRepository.findById(id)
            .map(invitationMapper::toDto);
    }

    /**
     * Delete the invitation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invitation : {}", id);
        invitationRepository.deleteById(id);
    }
}
