package com.cw.crm.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ContactHistory entity.
 */
public class ContactHistoryDTO implements Serializable {

    private Long id;

    private LocalDate date;

    @Lob
    private String notes;

    private Integer status;


    private Long participantId;

    private String participantFirstName;

    private Long userId;

    private String userLogin;

    private Long contactTypeId;

    private String contactTypeName;

    private Long contactSubTypeId;

    private String contactSubTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Long contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    public Long getContactSubTypeId() {
        return contactSubTypeId;
    }

    public void setContactSubTypeId(Long contactSubTypeId) {
        this.contactSubTypeId = contactSubTypeId;
    }

    public String getContactSubTypeName() {
        return contactSubTypeName;
    }

    public void setContactSubTypeName(String contactSubTypeName) {
        this.contactSubTypeName = contactSubTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactHistoryDTO contactHistoryDTO = (ContactHistoryDTO) o;
        if (contactHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status=" + getStatus() +
            ", participant=" + getParticipantId() +
            ", participant='" + getParticipantFirstName() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", contactType=" + getContactTypeId() +
            ", contactType='" + getContactTypeName() + "'" +
            ", contactSubType=" + getContactSubTypeId() +
            ", contactSubType='" + getContactSubTypeName() + "'" +
            "}";
    }
}
