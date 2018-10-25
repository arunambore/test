package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.UserProfileService;
import com.beroe.live.services.usermanagement.domain.UserProfile;
import com.beroe.live.services.usermanagement.repository.UserProfileRepository;
import com.beroe.live.services.usermanagement.service.dto.UserProfileDTO;
import com.beroe.live.services.usermanagement.service.mapper.UserProfileMapper;
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
 * Service Implementation for managing UserProfile.
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private UserProfileRepository userProfileRepository;

    private UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserProfileDTO save(UserProfileDTO userProfileDTO) {
        log.debug("Request to save UserProfile : {}", userProfileDTO);

        UserProfile userProfile = userProfileMapper.toEntity(userProfileDTO);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toDto(userProfile);
    }

    /**
     * Get all the userProfiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserProfileDTO> findAll() {
        log.debug("Request to get all UserProfiles");
        return userProfileRepository.findAll().stream()
            .map(userProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the userProfiles where Invitation is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserProfileDTO> findAllWhereInvitationIsNull() {
        log.debug("Request to get all userProfiles where Invitation is null");
        return StreamSupport
            .stream(userProfileRepository.findAll().spliterator(), false)
            .filter(userProfile -> userProfile.getInvitation() == null)
            .map(userProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileDTO> findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        return userProfileRepository.findById(id)
            .map(userProfileMapper::toDto);
    }

    /**
     * Delete the userProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.deleteById(id);
    }
}
