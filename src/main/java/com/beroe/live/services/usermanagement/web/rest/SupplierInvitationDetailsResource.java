package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.SupplierInvitationDetailsService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.SupplierInvitationDetailsDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing SupplierInvitationDetails.
 */
@RestController
@RequestMapping("/api")
public class SupplierInvitationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SupplierInvitationDetailsResource.class);

    private static final String ENTITY_NAME = "usermanagementSupplierInvitationDetails";

    private final SupplierInvitationDetailsService supplierInvitationDetailsService;

    public SupplierInvitationDetailsResource(SupplierInvitationDetailsService supplierInvitationDetailsService) {
        this.supplierInvitationDetailsService = supplierInvitationDetailsService;
    }

    /**
     * POST  /supplier-invitation-details : Create a new supplierInvitationDetails.
     *
     * @param supplierInvitationDetailsDTO the supplierInvitationDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplierInvitationDetailsDTO, or with status 400 (Bad Request) if the supplierInvitationDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supplier-invitation-details")
    @Timed
    public ResponseEntity<SupplierInvitationDetailsDTO> createSupplierInvitationDetails(@Valid @RequestBody SupplierInvitationDetailsDTO supplierInvitationDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierInvitationDetails : {}", supplierInvitationDetailsDTO);
        if (supplierInvitationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierInvitationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierInvitationDetailsDTO result = supplierInvitationDetailsService.save(supplierInvitationDetailsDTO);
        return ResponseEntity.created(new URI("/api/supplier-invitation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplier-invitation-details : Updates an existing supplierInvitationDetails.
     *
     * @param supplierInvitationDetailsDTO the supplierInvitationDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplierInvitationDetailsDTO,
     * or with status 400 (Bad Request) if the supplierInvitationDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplierInvitationDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supplier-invitation-details")
    @Timed
    public ResponseEntity<SupplierInvitationDetailsDTO> updateSupplierInvitationDetails(@Valid @RequestBody SupplierInvitationDetailsDTO supplierInvitationDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierInvitationDetails : {}", supplierInvitationDetailsDTO);
        if (supplierInvitationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierInvitationDetailsDTO result = supplierInvitationDetailsService.save(supplierInvitationDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplierInvitationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplier-invitation-details : get all the supplierInvitationDetails.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of supplierInvitationDetails in body
     */
    @GetMapping("/supplier-invitation-details")
    @Timed
    public List<SupplierInvitationDetailsDTO> getAllSupplierInvitationDetails(@RequestParam(required = false) String filter) {
        if ("invitation-is-null".equals(filter)) {
            log.debug("REST request to get all SupplierInvitationDetailss where invitation is null");
            return supplierInvitationDetailsService.findAllWhereInvitationIsNull();
        }
        log.debug("REST request to get all SupplierInvitationDetails");
        return supplierInvitationDetailsService.findAll();
    }

    /**
     * GET  /supplier-invitation-details/:id : get the "id" supplierInvitationDetails.
     *
     * @param id the id of the supplierInvitationDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplierInvitationDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supplier-invitation-details/{id}")
    @Timed
    public ResponseEntity<SupplierInvitationDetailsDTO> getSupplierInvitationDetails(@PathVariable Long id) {
        log.debug("REST request to get SupplierInvitationDetails : {}", id);
        Optional<SupplierInvitationDetailsDTO> supplierInvitationDetailsDTO = supplierInvitationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierInvitationDetailsDTO);
    }

    /**
     * DELETE  /supplier-invitation-details/:id : delete the "id" supplierInvitationDetails.
     *
     * @param id the id of the supplierInvitationDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supplier-invitation-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteSupplierInvitationDetails(@PathVariable Long id) {
        log.debug("REST request to delete SupplierInvitationDetails : {}", id);
        supplierInvitationDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
