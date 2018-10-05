package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.SystemConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SystemConfiguration and its DTO SystemConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemConfigurationMapper extends EntityMapper<SystemConfigurationDTO, SystemConfiguration> {


    @Mapping(target = "configurationApplicabilities", ignore = true)
    SystemConfiguration toEntity(SystemConfigurationDTO systemConfigurationDTO);

    default SystemConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemConfiguration systemConfiguration = new SystemConfiguration();
        systemConfiguration.setId(id);
        return systemConfiguration;
    }
}
