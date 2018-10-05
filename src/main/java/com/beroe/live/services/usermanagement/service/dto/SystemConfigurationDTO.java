package com.beroe.live.services.usermanagement.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SystemConfiguration entity.
 */
public class SystemConfigurationDTO implements Serializable {

    private Long id;

    @NotNull
    private String key;

    @NotNull
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemConfigurationDTO systemConfigurationDTO = (SystemConfigurationDTO) o;
        if (systemConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemConfigurationDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
