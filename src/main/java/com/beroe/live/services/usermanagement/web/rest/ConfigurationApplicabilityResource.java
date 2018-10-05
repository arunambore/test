package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.ConfigurationApplicabilityService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.ConfigurationApplicabilityDTO;
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
 * REST controller for managing ConfigurationApplicability.
 */
@RestController
@RequestMapping("/api")
public class ConfigurationApplicabilityResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurationApplicabilityResource.class);

    private static final String ENTITY_NAME = "usermanagementConfigurationApplicability";

    private final ConfigurationApplicabilityService configurationApplicabilityService;

    public ConfigurationApplicabilityResource(ConfigurationApplicabilityService configurationApplicabilityService) {
        this.configurationApplicabilityService = configurationApplicabilityService;
    }

    /**
     * POST  /configuration-applicabilities : Create a new configurationApplicability.
     *
     * @param configurationApplicabilityDTO the configurationApplicabilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configurationApplicabilityDTO, or with status 400 (Bad Request) if the configurationApplicability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configuration-applicabilities")
    @Timed
    public ResponseEntity<ConfigurationApplicabilityDTO> createConfigurationApplicability(@Valid @RequestBody ConfigurationApplicabilityDTO configurationApplicabilityDTO) throws URISyntaxException {
        log.debug("REST request to save ConfigurationApplicability : {}", configurationApplicabilityDTO);
        if (configurationApplicabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new configurationApplicability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigurationApplicabilityDTO result = configurationApplicabilityService.save(configurationApplicabilityDTO);
        return ResponseEntity.created(new URI("/api/configuration-applicabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configuration-applicabilities : Updates an existing configurationApplicability.
     *
     * @param configurationApplicabilityDTO the configurationApplicabilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configurationApplicabilityDTO,
     * or with status 400 (Bad Request) if the configurationApplicabilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the configurationApplicabilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configuration-applicabilities")
    @Timed
    public ResponseEntity<ConfigurationApplicabilityDTO> updateConfigurationApplicability(@Valid @RequestBody ConfigurationApplicabilityDTO configurationApplicabilityDTO) throws URISyntaxException {
        log.debug("REST request to update ConfigurationApplicability : {}", configurationApplicabilityDTO);
        if (configurationApplicabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigurationApplicabilityDTO result = configurationApplicabilityService.save(configurationApplicabilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configurationApplicabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configuration-applicabilities : get all the configurationApplicabilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of configurationApplicabilities in body
     */
    @GetMapping("/configuration-applicabilities")
    @Timed
    public List<ConfigurationApplicabilityDTO> getAllConfigurationApplicabilities() {
        log.debug("REST request to get all ConfigurationApplicabilities");
        return configurationApplicabilityService.findAll();
    }

    /**
     * GET  /configuration-applicabilities/:id : get the "id" configurationApplicability.
     *
     * @param id the id of the configurationApplicabilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configurationApplicabilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/configuration-applicabilities/{id}")
    @Timed
    public ResponseEntity<ConfigurationApplicabilityDTO> getConfigurationApplicability(@PathVariable Long id) {
        log.debug("REST request to get ConfigurationApplicability : {}", id);
        Optional<ConfigurationApplicabilityDTO> configurationApplicabilityDTO = configurationApplicabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configurationApplicabilityDTO);
    }

    /**
     * DELETE  /configuration-applicabilities/:id : delete the "id" configurationApplicability.
     *
     * @param id the id of the configurationApplicabilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configuration-applicabilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfigurationApplicability(@PathVariable Long id) {
        log.debug("REST request to delete ConfigurationApplicability : {}", id);
        configurationApplicabilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
