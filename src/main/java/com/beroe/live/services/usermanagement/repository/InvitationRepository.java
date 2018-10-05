package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.Invitation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Invitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
