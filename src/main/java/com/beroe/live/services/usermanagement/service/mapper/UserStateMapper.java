package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.UserStateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserState and its DTO UserStateDTO.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface UserStateMapper extends EntityMapper<UserStateDTO, UserState> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    UserStateDTO toDto(UserState userState);

    @Mapping(source = "userProfileId", target = "userProfile")
    UserState toEntity(UserStateDTO userStateDTO);

    default UserState fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserState userState = new UserState();
        userState.setId(id);
        return userState;
    }
}
