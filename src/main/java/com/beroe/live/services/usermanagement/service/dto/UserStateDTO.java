package com.beroe.live.services.usermanagement.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserState entity.
 */
public class UserStateDTO implements Serializable {

    private Long id;

    @NotNull
    private String state;

    private LocalDate date;

    private Long userProfileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserStateDTO userStateDTO = (UserStateDTO) o;
        if (userStateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userStateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserStateDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", date='" + getDate() + "'" +
            ", userProfile=" + getUserProfileId() +
            "}";
    }
}
