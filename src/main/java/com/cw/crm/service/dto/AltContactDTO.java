package com.cw.crm.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the AltContact entity.
 */
public class AltContactDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String relationship;

    @NotNull
    private String phone;

    @Lob
    private String notes;


    private Long participantId;

    private String participantFirstName;

    private Long createdById;

    private String createdByLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getParticipantFirstName() {
        return participantFirstName;
    }

    public void setParticipantFirstName(String participantFirstName) {
        this.participantFirstName = participantFirstName;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userId) {
        this.createdById = userId;
    }

    public String getCreatedByLogin() {
        return createdByLogin;
    }

    public void setCreatedByLogin(String userLogin) {
        this.createdByLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AltContactDTO altContactDTO = (AltContactDTO) o;
        if (altContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), altContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AltContactDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", relationship='" + getRelationship() + "'" +
            ", phone='" + getPhone() + "'" +
            ", notes='" + getNotes() + "'" +
            ", participant=" + getParticipantId() +
            ", participant='" + getParticipantFirstName() + "'" +
            ", createdBy=" + getCreatedById() +
            ", createdBy='" + getCreatedByLogin() + "'" +
            "}";
    }
}
