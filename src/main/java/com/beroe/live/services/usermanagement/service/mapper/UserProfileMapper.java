package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.UserProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserProfile and its DTO UserProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface UserProfileMapper extends EntityMapper<UserProfileDTO, UserProfile> {

    @Mapping(source = "company.id", target = "companyId")
    UserProfileDTO toDto(UserProfile userProfile);

    @Mapping(source = "companyId", target = "company")
    @Mapping(target = "userStates", ignore = true)
    @Mapping(target = "invitation", ignore = true)
    UserProfile toEntity(UserProfileDTO userProfileDTO);

    default UserProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        return userProfile;
    }
}
