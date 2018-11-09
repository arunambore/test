package com.beroe.live.services.usermanagement.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RestrictedDomains entity.
 */
public class RestrictedDomainsDTO implements Serializable {

    private Long id;

    @NotNull
    private String domainName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestrictedDomainsDTO restrictedDomainsDTO = (RestrictedDomainsDTO) o;
        if (restrictedDomainsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restrictedDomainsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestrictedDomainsDTO{" +
            "id=" + getId() +
            ", domainName='" + getDomainName() + "'" +
            "}";
    }
}
