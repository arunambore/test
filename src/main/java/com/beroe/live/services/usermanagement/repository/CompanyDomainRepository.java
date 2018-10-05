package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.CompanyDomain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompanyDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDomainRepository extends JpaRepository<CompanyDomain, Long> {

}
