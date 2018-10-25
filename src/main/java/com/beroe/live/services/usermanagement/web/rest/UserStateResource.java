package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.UserStateService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.UserStateDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserState.
 */
@RestController
@RequestMapping("/api")
public class UserStateResource {

    private final Logger log = LoggerFactory.getLogger(UserStateResource.class);

    private static final String ENTITY_NAME = "usermanagementUserState";

    private UserStateService userStateService;

    public UserStateResource(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    /**
     * POST  /user-states : Create a new userState.
     *
     * @param userStateDTO the userStateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userStateDTO, or with status 400 (Bad Request) if the userState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-states")
    @Timed
    public ResponseEntity<UserStateDTO> createUserState(@Valid @RequestBody UserStateDTO userStateDTO) throws URISyntaxException {
        log.debug("REST request to save UserState : {}", userStateDTO);
        if (userStateDTO.getId() != null) {
            throw new BadRequestAlertException("A new userState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserStateDTO result = userStateService.save(userStateDTO);
        return ResponseEntity.created(new URI("/api/user-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-states : Updates an existing userState.
     *
     * @param userStateDTO the userStateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userStateDTO,
     * or with status 400 (Bad Request) if the userStateDTO is not valid,
     * or with status 500 (Internal Server Error) if the userStateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-states")
    @Timed
    public ResponseEntity<UserStateDTO> updateUserState(@Valid @RequestBody UserStateDTO userStateDTO) throws URISyntaxException {
        log.debug("REST request to update UserState : {}", userStateDTO);
        if (userStateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserStateDTO result = userStateService.save(userStateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userStateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-states : get all the userStates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userStates in body
     */
    @GetMapping("/user-states")
    @Timed
    public List<UserStateDTO> getAllUserStates() {
        log.debug("REST request to get all UserStates");
        return userStateService.findAll();
    }

    /**
     * GET  /user-states/:id : get the "id" userState.
     *
     * @param id the id of the userStateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userStateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-states/{id}")
    @Timed
    public ResponseEntity<UserStateDTO> getUserState(@PathVariable Long id) {
        log.debug("REST request to get UserState : {}", id);
        Optional<UserStateDTO> userStateDTO = userStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userStateDTO);
    }

    /**
     * DELETE  /user-states/:id : delete the "id" userState.
     *
     * @param id the id of the userStateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-states/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserState(@PathVariable Long id) {
        log.debug("REST request to delete UserState : {}", id);
        userStateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
