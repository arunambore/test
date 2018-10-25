package com.beroe.live.services.usermanagement.service.impl;

import com.beroe.live.services.usermanagement.service.UserStateService;
import com.beroe.live.services.usermanagement.domain.UserState;
import com.beroe.live.services.usermanagement.repository.UserStateRepository;
import com.beroe.live.services.usermanagement.service.dto.UserStateDTO;
import com.beroe.live.services.usermanagement.service.mapper.UserStateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserState.
 */
@Service
@Transactional
public class UserStateServiceImpl implements UserStateService {

    private final Logger log = LoggerFactory.getLogger(UserStateServiceImpl.class);

    private UserStateRepository userStateRepository;

    private UserStateMapper userStateMapper;

    public UserStateServiceImpl(UserStateRepository userStateRepository, UserStateMapper userStateMapper) {
        this.userStateRepository = userStateRepository;
        this.userStateMapper = userStateMapper;
    }

    /**
     * Save a userState.
     *
     * @param userStateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserStateDTO save(UserStateDTO userStateDTO) {
        log.debug("Request to save UserState : {}", userStateDTO);

        UserState userState = userStateMapper.toEntity(userStateDTO);
        userState = userStateRepository.save(userState);
        return userStateMapper.toDto(userState);
    }

    /**
     * Get all the userStates.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserStateDTO> findAll() {
        log.debug("Request to get all UserStates");
        return userStateRepository.findAll().stream()
            .map(userStateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userState by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserStateDTO> findOne(Long id) {
        log.debug("Request to get UserState : {}", id);
        return userStateRepository.findById(id)
            .map(userStateMapper::toDto);
    }

    /**
     * Delete the userState by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserState : {}", id);
        userStateRepository.deleteById(id);
    }
}
