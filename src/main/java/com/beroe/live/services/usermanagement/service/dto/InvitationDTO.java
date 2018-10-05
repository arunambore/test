package com.beroe.live.services.usermanagement.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Invitation entity.
 */
public class InvitationDTO implements Serializable {

    private Long id;

    @NotNull
    private String key;

    private Long pin;

    @NotNull
    private LocalDate invitationDate;

    private LocalDate validTill;

    private Boolean isInvalid;

    private Long userProfileId;

    private Long companyId;

    private Long supplierInvitationDetailsId;

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

    public Long getPin() {
        return pin;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }

    public LocalDate getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(LocalDate invitationDate) {
        this.invitationDate = invitationDate;
    }

    public LocalDate getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public Boolean isIsInvalid() {
        return isInvalid;
    }

    public void setIsInvalid(Boolean isInvalid) {
        this.isInvalid = isInvalid;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getSupplierInvitationDetailsId() {
        return supplierInvitationDetailsId;
    }

    public void setSupplierInvitationDetailsId(Long supplierInvitationDetailsId) {
        this.supplierInvitationDetailsId = supplierInvitationDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvitationDTO invitationDTO = (InvitationDTO) o;
        if (invitationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invitationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvitationDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", pin=" + getPin() +
            ", invitationDate='" + getInvitationDate() + "'" +
            ", validTill='" + getValidTill() + "'" +
            ", isInvalid='" + isIsInvalid() + "'" +
            ", userProfile=" + getUserProfileId() +
            ", company=" + getCompanyId() +
            ", supplierInvitationDetails=" + getSupplierInvitationDetailsId() +
            "}";
    }
}
