package com.beroe.live.services.usermanagement.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ConfigurationApplicability entity.
 */
public class ConfigurationApplicabilityDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long systemConfigurationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSystemConfigurationId() {
        return systemConfigurationId;
    }

    public void setSystemConfigurationId(Long systemConfigurationId) {
        this.systemConfigurationId = systemConfigurationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfigurationApplicabilityDTO configurationApplicabilityDTO = (ConfigurationApplicabilityDTO) o;
        if (configurationApplicabilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configurationApplicabilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfigurationApplicabilityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", systemConfiguration=" + getSystemConfigurationId() +
            "}";
    }
}
