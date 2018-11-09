package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.RestrictedDomainsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RestrictedDomains and its DTO RestrictedDomainsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestrictedDomainsMapper extends EntityMapper<RestrictedDomainsDTO, RestrictedDomains> {



    default RestrictedDomains fromId(Long id) {
        if (id == null) {
            return null;
        }
        RestrictedDomains restrictedDomains = new RestrictedDomains();
        restrictedDomains.setId(id);
        return restrictedDomains;
    }
}
