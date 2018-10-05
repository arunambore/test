package com.beroe.live.services.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.beroe.live.services.usermanagement.domain.enumeration.SupplierDataProvider;

/**
 * Entity
 */
@ApiModel(description = "Entity")
@Entity
@Table(name = "supplier_invitation_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SupplierInvitationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "supplier_data_provider_key", nullable = false)
    private String supplierDataProviderKey;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_provider", nullable = false)
    private SupplierDataProvider dataProvider;

    @OneToOne(mappedBy = "supplierInvitationDetails")
    @JsonIgnore
    private Invitation invitation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierDataProviderKey() {
        return supplierDataProviderKey;
    }

    public SupplierInvitationDetails supplierDataProviderKey(String supplierDataProviderKey) {
        this.supplierDataProviderKey = supplierDataProviderKey;
        return this;
    }

    public void setSupplierDataProviderKey(String supplierDataProviderKey) {
        this.supplierDataProviderKey = supplierDataProviderKey;
    }

    public SupplierDataProvider getDataProvider() {
        return dataProvider;
    }

    public SupplierInvitationDetails dataProvider(SupplierDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        return this;
    }

    public void setDataProvider(SupplierDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public SupplierInvitationDetails invitation(Invitation invitation) {
        this.invitation = invitation;
        return this;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
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
        SupplierInvitationDetails supplierInvitationDetails = (SupplierInvitationDetails) o;
        if (supplierInvitationDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierInvitationDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierInvitationDetails{" +
            "id=" + getId() +
            ", supplierDataProviderKey='" + getSupplierDataProviderKey() + "'" +
            ", dataProvider='" + getDataProvider() + "'" +
            "}";
    }
}
