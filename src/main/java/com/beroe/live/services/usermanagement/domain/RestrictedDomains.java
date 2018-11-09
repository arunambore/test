package com.beroe.live.services.usermanagement.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RestrictedDomains.
 */
@Entity
@Table(name = "restricted_domains")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RestrictedDomains implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "domain_name", nullable = false)
    private String domainName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public RestrictedDomains domainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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
        RestrictedDomains restrictedDomains = (RestrictedDomains) o;
        if (restrictedDomains.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restrictedDomains.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestrictedDomains{" +
            "id=" + getId() +
            ", domainName='" + getDomainName() + "'" +
            "}";
    }
}
