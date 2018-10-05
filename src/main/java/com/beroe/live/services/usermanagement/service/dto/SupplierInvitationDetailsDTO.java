package com.beroe.live.services.usermanagement.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.beroe.live.services.usermanagement.domain.enumeration.SupplierDataProvider;

/**
 * A DTO for the SupplierInvitationDetails entity.
 */
public class SupplierInvitationDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String supplierDataProviderKey;

    @NotNull
    private SupplierDataProvider dataProvider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierDataProviderKey() {
        return supplierDataProviderKey;
    }

    public void setSupplierDataProviderKey(String supplierDataProviderKey) {
        this.supplierDataProviderKey = supplierDataProviderKey;
    }

    public SupplierDataProvider getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(SupplierDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = (SupplierInvitationDetailsDTO) o;
        if (supplierInvitationDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierInvitationDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierInvitationDetailsDTO{" +
            "id=" + getId() +
            ", supplierDataProviderKey='" + getSupplierDataProviderKey() + "'" +
            ", dataProvider='" + getDataProvider() + "'" +
            "}";
    }
}
