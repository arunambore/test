package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.RestrictedDomainsService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.RestrictedDomainsDTO;
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
 * REST controller for managing RestrictedDomains.
 */
@RestController
@RequestMapping("/api")
public class RestrictedDomainsResource {

    private final Logger log = LoggerFactory.getLogger(RestrictedDomainsResource.class);

    private static final String ENTITY_NAME = "usermanagementRestrictedDomains";

    private final RestrictedDomainsService restrictedDomainsService;

    public RestrictedDomainsResource(RestrictedDomainsService restrictedDomainsService) {
        this.restrictedDomainsService = restrictedDomainsService;
    }

    /**
     * POST  /restricted-domains : Create a new restrictedDomains.
     *
     * @param restrictedDomainsDTO the restrictedDomainsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restrictedDomainsDTO, or with status 400 (Bad Request) if the restrictedDomains has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restricted-domains")
    @Timed
    public ResponseEntity<RestrictedDomainsDTO> createRestrictedDomains(@Valid @RequestBody RestrictedDomainsDTO restrictedDomainsDTO) throws URISyntaxException {
        log.debug("REST request to save RestrictedDomains : {}", restrictedDomainsDTO);
        if (restrictedDomainsDTO.getId() != null) {
            throw new BadRequestAlertException("A new restrictedDomains cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestrictedDomainsDTO result = restrictedDomainsService.save(restrictedDomainsDTO);
        return ResponseEntity.created(new URI("/api/restricted-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restricted-domains : Updates an existing restrictedDomains.
     *
     * @param restrictedDomainsDTO the restrictedDomainsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restrictedDomainsDTO,
     * or with status 400 (Bad Request) if the restrictedDomainsDTO is not valid,
     * or with status 500 (Internal Server Error) if the restrictedDomainsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restricted-domains")
    @Timed
    public ResponseEntity<RestrictedDomainsDTO> updateRestrictedDomains(@Valid @RequestBody RestrictedDomainsDTO restrictedDomainsDTO) throws URISyntaxException {
        log.debug("REST request to update RestrictedDomains : {}", restrictedDomainsDTO);
        if (restrictedDomainsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestrictedDomainsDTO result = restrictedDomainsService.save(restrictedDomainsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restrictedDomainsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restricted-domains : get all the restrictedDomains.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restrictedDomains in body
     */
    @GetMapping("/restricted-domains")
    @Timed
    public List<RestrictedDomainsDTO> getAllRestrictedDomains() {
        log.debug("REST request to get all RestrictedDomains");
        return restrictedDomainsService.findAll();
    }

    /**
     * GET  /restricted-domains/:id : get the "id" restrictedDomains.
     *
     * @param id the id of the restrictedDomainsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restrictedDomainsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/restricted-domains/{id}")
    @Timed
    public ResponseEntity<RestrictedDomainsDTO> getRestrictedDomains(@PathVariable Long id) {
        log.debug("REST request to get RestrictedDomains : {}", id);
        Optional<RestrictedDomainsDTO> restrictedDomainsDTO = restrictedDomainsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restrictedDomainsDTO);
    }

    /**
     * DELETE  /restricted-domains/:id : delete the "id" restrictedDomains.
     *
     * @param id the id of the restrictedDomainsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restricted-domains/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestrictedDomains(@PathVariable Long id) {
        log.debug("REST request to delete RestrictedDomains : {}", id);
        restrictedDomainsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
