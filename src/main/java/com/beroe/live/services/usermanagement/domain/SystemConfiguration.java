package com.beroe.live.services.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SystemConfiguration.
 */
@Entity
@Table(name = "system_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SystemConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @OneToMany(mappedBy = "systemConfiguration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConfigurationApplicability> configurationApplicabilities = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public SystemConfiguration key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public SystemConfiguration value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<ConfigurationApplicability> getConfigurationApplicabilities() {
        return configurationApplicabilities;
    }

    public SystemConfiguration configurationApplicabilities(Set<ConfigurationApplicability> configurationApplicabilities) {
        this.configurationApplicabilities = configurationApplicabilities;
        return this;
    }

    public SystemConfiguration addConfigurationApplicability(ConfigurationApplicability configurationApplicability) {
        this.configurationApplicabilities.add(configurationApplicability);
        configurationApplicability.setSystemConfiguration(this);
        return this;
    }

    public SystemConfiguration removeConfigurationApplicability(ConfigurationApplicability configurationApplicability) {
        this.configurationApplicabilities.remove(configurationApplicability);
        configurationApplicability.setSystemConfiguration(null);
        return this;
    }

    public void setConfigurationApplicabilities(Set<ConfigurationApplicability> configurationApplicabilities) {
        this.configurationApplicabilities = configurationApplicabilities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemConfiguration systemConfiguration = (SystemConfiguration) o;
        if (systemConfiguration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemConfiguration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemConfiguration{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
