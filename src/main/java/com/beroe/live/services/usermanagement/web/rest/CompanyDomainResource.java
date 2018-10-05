package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.CompanyDomainService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.CompanyDomainDTO;
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
 * REST controller for managing CompanyDomain.
 */
@RestController
@RequestMapping("/api")
public class CompanyDomainResource {

    private final Logger log = LoggerFactory.getLogger(CompanyDomainResource.class);

    private static final String ENTITY_NAME = "usermanagementCompanyDomain";

    private final CompanyDomainService companyDomainService;

    public CompanyDomainResource(CompanyDomainService companyDomainService) {
        this.companyDomainService = companyDomainService;
    }

    /**
     * POST  /company-domains : Create a new companyDomain.
     *
     * @param companyDomainDTO the companyDomainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDomainDTO, or with status 400 (Bad Request) if the companyDomain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-domains")
    @Timed
    public ResponseEntity<CompanyDomainDTO> createCompanyDomain(@Valid @RequestBody CompanyDomainDTO companyDomainDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyDomain : {}", companyDomainDTO);
        if (companyDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyDomainDTO result = companyDomainService.save(companyDomainDTO);
        return ResponseEntity.created(new URI("/api/company-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-domains : Updates an existing companyDomain.
     *
     * @param companyDomainDTO the companyDomainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDomainDTO,
     * or with status 400 (Bad Request) if the companyDomainDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyDomainDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-domains")
    @Timed
    public ResponseEntity<CompanyDomainDTO> updateCompanyDomain(@Valid @RequestBody CompanyDomainDTO companyDomainDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyDomain : {}", companyDomainDTO);
        if (companyDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyDomainDTO result = companyDomainService.save(companyDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-domains : get all the companyDomains.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyDomains in body
     */
    @GetMapping("/company-domains")
    @Timed
    public List<CompanyDomainDTO> getAllCompanyDomains() {
        log.debug("REST request to get all CompanyDomains");
        return companyDomainService.findAll();
    }

    /**
     * GET  /company-domains/:id : get the "id" companyDomain.
     *
     * @param id the id of the companyDomainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDomainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-domains/{id}")
    @Timed
    public ResponseEntity<CompanyDomainDTO> getCompanyDomain(@PathVariable Long id) {
        log.debug("REST request to get CompanyDomain : {}", id);
        Optional<CompanyDomainDTO> companyDomainDTO = companyDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyDomainDTO);
    }

    /**
     * DELETE  /company-domains/:id : delete the "id" companyDomain.
     *
     * @param id the id of the companyDomainDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-domains/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyDomain(@PathVariable Long id) {
        log.debug("REST request to delete CompanyDomain : {}", id);
        companyDomainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
