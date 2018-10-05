package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.ConfigurationApplicability;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConfigurationApplicability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationApplicabilityRepository extends JpaRepository<ConfigurationApplicability, Long> {

}
