package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.SupplierInvitationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplierInvitationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierInvitationDetailsRepository extends JpaRepository<SupplierInvitationDetails, Long> {

}
