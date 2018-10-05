package com.beroe.live.services.usermanagement.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity Invitation
 */
@ApiModel(description = "Entity Invitation")
@Entity
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Key is UUID which can be used to fetch details about all the child SupplierDataRequests
     * 
     * It could be sent in the email and is used to populate the request details for the company user
     */
    @NotNull
    @ApiModelProperty(value = "Key is UUID which can be used to fetch details about all the child SupplierDataRequests It could be sent in the email and is used to populate the request details for the company user", required = true)
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @Column(name = "pin")
    private Long pin;

    @NotNull
    @Column(name = "invitation_date", nullable = false)
    private LocalDate invitationDate;

    /**
     * validTill represents the date till when this invitation is vaild. If someone tries to onboard after this date, registration will be rejected.
     */
    @ApiModelProperty(value = "validTill represents the date till when this invitation is vaild. If someone tries to onboard after this date, registration will be rejected.")
    @Column(name = "valid_till")
    private LocalDate validTill;

    /**
     * If we need to deactivate / invalidate any invitation, set its isInvalid attribute to true
     */
    @ApiModelProperty(value = "If we need to deactivate / invalidate any invitation, set its isInvalid attribute to true")
    @Column(name = "is_invalid")
    private Boolean isInvalid;

    @OneToOne    @JoinColumn(unique = true)
    private UserProfile userProfile;

    @OneToOne    @JoinColumn(unique = true)
    private Company company;

    @OneToOne    @JoinColumn(unique = true)
    private SupplierInvitationDetails supplierInvitationDetails;

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

    public Invitation key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getPin() {
        return pin;
    }

    public Invitation pin(Long pin) {
        this.pin = pin;
        return this;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }

    public LocalDate getInvitationDate() {
        return invitationDate;
    }

    public Invitation invitationDate(LocalDate invitationDate) {
        this.invitationDate = invitationDate;
        return this;
    }

    public void setInvitationDate(LocalDate invitationDate) {
        this.invitationDate = invitationDate;
    }

    public LocalDate getValidTill() {
        return validTill;
    }

    public Invitation validTill(LocalDate validTill) {
        this.validTill = validTill;
        return this;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public Boolean isIsInvalid() {
        return isInvalid;
    }

    public Invitation isInvalid(Boolean isInvalid) {
        this.isInvalid = isInvalid;
        return this;
    }

    public void setIsInvalid(Boolean isInvalid) {
        this.isInvalid = isInvalid;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Invitation userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Company getCompany() {
        return company;
    }

    public Invitation company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public SupplierInvitationDetails getSupplierInvitationDetails() {
        return supplierInvitationDetails;
    }

    public Invitation supplierInvitationDetails(SupplierInvitationDetails supplierInvitationDetails) {
        this.supplierInvitationDetails = supplierInvitationDetails;
        return this;
    }

    public void setSupplierInvitationDetails(SupplierInvitationDetails supplierInvitationDetails) {
        this.supplierInvitationDetails = supplierInvitationDetails;
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
        Invitation invitation = (Invitation) o;
        if (invitation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invitation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invitation{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", pin=" + getPin() +
            ", invitationDate='" + getInvitationDate() + "'" +
            ", validTill='" + getValidTill() + "'" +
            ", isInvalid='" + isIsInvalid() + "'" +
            "}";
    }
}
