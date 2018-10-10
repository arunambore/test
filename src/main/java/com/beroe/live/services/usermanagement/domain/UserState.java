package com.beroe.live.services.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A UserState.
 */
@Entity
@Table(name = "user_state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "jhi_value")
    private String value;

    @Column(name = "jhi_date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("userStates")
    private UserProfile userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public UserState state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getValue() {
        return value;
    }

    public UserState value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public UserState date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public UserState userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
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
        UserState userState = (UserState) o;
        if (userState.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userState.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserState{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", value='" + getValue() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
