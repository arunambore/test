package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.ConfigurationApplicabilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConfigurationApplicability and its DTO ConfigurationApplicabilityDTO.
 */
@Mapper(componentModel = "spring", uses = {SystemConfigurationMapper.class})
public interface ConfigurationApplicabilityMapper extends EntityMapper<ConfigurationApplicabilityDTO, ConfigurationApplicability> {

    @Mapping(source = "systemConfiguration.id", target = "systemConfigurationId")
    ConfigurationApplicabilityDTO toDto(ConfigurationApplicability configurationApplicability);

    @Mapping(source = "systemConfigurationId", target = "systemConfiguration")
    ConfigurationApplicability toEntity(ConfigurationApplicabilityDTO configurationApplicabilityDTO);

    default ConfigurationApplicability fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConfigurationApplicability configurationApplicability = new ConfigurationApplicability();
        configurationApplicability.setId(id);
        return configurationApplicability;
    }
}
