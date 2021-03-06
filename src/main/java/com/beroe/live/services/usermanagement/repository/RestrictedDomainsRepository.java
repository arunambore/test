package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.RestrictedDomains;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RestrictedDomains entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestrictedDomainsRepository extends JpaRepository<RestrictedDomains, Long> {

}
