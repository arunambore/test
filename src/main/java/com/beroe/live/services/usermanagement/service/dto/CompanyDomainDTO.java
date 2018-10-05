package com.beroe.live.services.usermanagement.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CompanyDomain entity.
 */
public class CompanyDomainDTO implements Serializable {

    private Long id;

    @NotNull
    private String domain;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDomainDTO companyDomainDTO = (CompanyDomainDTO) o;
        if (companyDomainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDomainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDomainDTO{" +
            "id=" + getId() +
            ", domain='" + getDomain() + "'" +
            ", company=" + getCompanyId() +
            "}";
    }
}
