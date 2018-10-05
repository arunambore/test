package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.InvitationService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.InvitationDTO;
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
 * REST controller for managing Invitation.
 */
@RestController
@RequestMapping("/api")
public class InvitationResource {

    private final Logger log = LoggerFactory.getLogger(InvitationResource.class);

    private static final String ENTITY_NAME = "usermanagementInvitation";

    private final InvitationService invitationService;

    public InvitationResource(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    /**
     * POST  /invitations : Create a new invitation.
     *
     * @param invitationDTO the invitationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invitationDTO, or with status 400 (Bad Request) if the invitation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invitations")
    @Timed
    public ResponseEntity<InvitationDTO> createInvitation(@Valid @RequestBody InvitationDTO invitationDTO) throws URISyntaxException {
        log.debug("REST request to save Invitation : {}", invitationDTO);
        if (invitationDTO.getId() != null) {
            throw new BadRequestAlertException("A new invitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitationDTO result = invitationService.save(invitationDTO);
        return ResponseEntity.created(new URI("/api/invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invitations : Updates an existing invitation.
     *
     * @param invitationDTO the invitationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invitationDTO,
     * or with status 400 (Bad Request) if the invitationDTO is not valid,
     * or with status 500 (Internal Server Error) if the invitationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invitations")
    @Timed
    public ResponseEntity<InvitationDTO> updateInvitation(@Valid @RequestBody InvitationDTO invitationDTO) throws URISyntaxException {
        log.debug("REST request to update Invitation : {}", invitationDTO);
        if (invitationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitationDTO result = invitationService.save(invitationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invitationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invitations : get all the invitations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invitations in body
     */
    @GetMapping("/invitations")
    @Timed
    public List<InvitationDTO> getAllInvitations() {
        log.debug("REST request to get all Invitations");
        return invitationService.findAll();
    }

    /**
     * GET  /invitations/:id : get the "id" invitation.
     *
     * @param id the id of the invitationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invitationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invitations/{id}")
    @Timed
    public ResponseEntity<InvitationDTO> getInvitation(@PathVariable Long id) {
        log.debug("REST request to get Invitation : {}", id);
        Optional<InvitationDTO> invitationDTO = invitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invitationDTO);
    }

    /**
     * DELETE  /invitations/:id : delete the "id" invitation.
     *
     * @param id the id of the invitationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invitations/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        log.debug("REST request to delete Invitation : {}", id);
        invitationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
