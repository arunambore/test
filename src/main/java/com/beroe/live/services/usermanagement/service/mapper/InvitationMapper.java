package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.InvitationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invitation and its DTO InvitationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, CompanyMapper.class, SupplierInvitationDetailsMapper.class})
public interface InvitationMapper extends EntityMapper<InvitationDTO, Invitation> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "supplierInvitationDetails.id", target = "supplierInvitationDetailsId")
    InvitationDTO toDto(Invitation invitation);

    @Mapping(source = "userProfileId", target = "userProfile")
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "supplierInvitationDetailsId", target = "supplierInvitationDetails")
    Invitation toEntity(InvitationDTO invitationDTO);

    default Invitation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invitation invitation = new Invitation();
        invitation.setId(id);
        return invitation;
    }
}
