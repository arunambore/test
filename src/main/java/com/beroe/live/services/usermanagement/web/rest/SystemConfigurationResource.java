package com.beroe.live.services.usermanagement.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.beroe.live.services.usermanagement.service.SystemConfigurationService;
import com.beroe.live.services.usermanagement.web.rest.errors.BadRequestAlertException;
import com.beroe.live.services.usermanagement.web.rest.util.HeaderUtil;
import com.beroe.live.services.usermanagement.service.dto.SystemConfigurationDTO;
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
 * REST controller for managing SystemConfiguration.
 */
@RestController
@RequestMapping("/api")
public class SystemConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(SystemConfigurationResource.class);

    private static final String ENTITY_NAME = "usermanagementSystemConfiguration";

    private SystemConfigurationService systemConfigurationService;

    public SystemConfigurationResource(SystemConfigurationService systemConfigurationService) {
        this.systemConfigurationService = systemConfigurationService;
    }

    /**
     * POST  /system-configurations : Create a new systemConfiguration.
     *
     * @param systemConfigurationDTO the systemConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemConfigurationDTO, or with status 400 (Bad Request) if the systemConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-configurations")
    @Timed
    public ResponseEntity<SystemConfigurationDTO> createSystemConfiguration(@Valid @RequestBody SystemConfigurationDTO systemConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save SystemConfiguration : {}", systemConfigurationDTO);
        if (systemConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemConfigurationDTO result = systemConfigurationService.save(systemConfigurationDTO);
        return ResponseEntity.created(new URI("/api/system-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-configurations : Updates an existing systemConfiguration.
     *
     * @param systemConfigurationDTO the systemConfigurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemConfigurationDTO,
     * or with status 400 (Bad Request) if the systemConfigurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemConfigurationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-configurations")
    @Timed
    public ResponseEntity<SystemConfigurationDTO> updateSystemConfiguration(@Valid @RequestBody SystemConfigurationDTO systemConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update SystemConfiguration : {}", systemConfigurationDTO);
        if (systemConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemConfigurationDTO result = systemConfigurationService.save(systemConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-configurations : get all the systemConfigurations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of systemConfigurations in body
     */
    @GetMapping("/system-configurations")
    @Timed
    public List<SystemConfigurationDTO> getAllSystemConfigurations() {
        log.debug("REST request to get all SystemConfigurations");
        return systemConfigurationService.findAll();
    }

    /**
     * GET  /system-configurations/:id : get the "id" systemConfiguration.
     *
     * @param id the id of the systemConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/system-configurations/{id}")
    @Timed
    public ResponseEntity<SystemConfigurationDTO> getSystemConfiguration(@PathVariable Long id) {
        log.debug("REST request to get SystemConfiguration : {}", id);
        Optional<SystemConfigurationDTO> systemConfigurationDTO = systemConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemConfigurationDTO);
    }

    /**
     * DELETE  /system-configurations/:id : delete the "id" systemConfiguration.
     *
     * @param id the id of the systemConfigurationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSystemConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete SystemConfiguration : {}", id);
        systemConfigurationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
