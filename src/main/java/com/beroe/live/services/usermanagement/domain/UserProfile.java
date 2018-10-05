package com.beroe.live.services.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Entity UserProfile
 * All the user related information apart from password
 */
@ApiModel(description = "Entity UserProfile All the user related information apart from password")
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "title")
    private String title;

    @OneToOne    @JoinColumn(unique = true)
    private Company company;

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserState> userStates = new HashSet<>();
    @OneToOne(mappedBy = "userProfile")
    @JsonIgnore
    private Invitation invitation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public UserProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public UserProfile uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public UserProfile fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTitle() {
        return title;
    }

    public UserProfile title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Company getCompany() {
        return company;
    }

    public UserProfile company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<UserState> getUserStates() {
        return userStates;
    }

    public UserProfile userStates(Set<UserState> userStates) {
        this.userStates = userStates;
        return this;
    }

    public UserProfile addUserState(UserState userState) {
        this.userStates.add(userState);
        userState.setUserProfile(this);
        return this;
    }

    public UserProfile removeUserState(UserState userState) {
        this.userStates.remove(userState);
        userState.setUserProfile(null);
        return this;
    }

    public void setUserStates(Set<UserState> userStates) {
        this.userStates = userStates;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public UserProfile invitation(Invitation invitation) {
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
        UserProfile userProfile = (UserProfile) o;
        if (userProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
