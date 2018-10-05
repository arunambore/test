package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.SystemConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SystemConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {

}
