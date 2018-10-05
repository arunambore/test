package com.beroe.live.services.usermanagement.service;

import com.beroe.live.services.usermanagement.service.dto.CompanyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     * Get all the companies.
     *
     * @return the list of entities
     */
    List<CompanyDTO> findAll();
    /**
     * Get all the CompanyDTO where UserProfile is null.
     *
     * @return the list of entities
     */
    List<CompanyDTO> findAllWhereUserProfileIsNull();
    /**
     * Get all the CompanyDTO where Invitation is null.
     *
     * @return the list of entities
     */
    List<CompanyDTO> findAllWhereInvitationIsNull();


    /**
     * Get the "id" company.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompanyDTO> findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
