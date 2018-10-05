package com.beroe.live.services.usermanagement.repository;

import com.beroe.live.services.usermanagement.domain.UserState;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {

}
