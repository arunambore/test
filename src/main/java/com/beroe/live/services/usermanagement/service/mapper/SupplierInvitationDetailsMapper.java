package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.SupplierInvitationDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SupplierInvitationDetails and its DTO SupplierInvitationDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierInvitationDetailsMapper extends EntityMapper<SupplierInvitationDetailsDTO, SupplierInvitationDetails> {


    @Mapping(target = "invitation", ignore = true)
    SupplierInvitationDetails toEntity(SupplierInvitationDetailsDTO supplierInvitationDetailsDTO);

    default SupplierInvitationDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierInvitationDetails supplierInvitationDetails = new SupplierInvitationDetails();
        supplierInvitationDetails.setId(id);
        return supplierInvitationDetails;
    }
}
