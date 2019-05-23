package com.cw.crm.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Action entity.
 */
public class ActionDTO implements Serializable {

    private Long id;

    private LocalDate dueDate;

    private Instant startDateTime;

    private Instant endDateTime;

    @Lob
    private String notes;

    private Long participantId;

    private String participantFirstName;

    private String participantLastName;

    private Long userId;

    private String userLogin;

    private String userFirstName;

    private String userLastName;

    private Long actionSubTypeId;

    private String actionSubTypeName;

    private Long actionTypeId;

    private String actionTypeName;

    private Long actionStatusId;

    private String actionStatusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
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

    public Long getActionSubTypeId() {
        return actionSubTypeId;
    }

    public void setActionSubTypeId(Long actionSubTypeId) {
        this.actionSubTypeId = actionSubTypeId;
    }

    public String getActionSubTypeName() {
        return actionSubTypeName;
    }

    public void setActionSubTypeName(String actionSubTypeName) {
        this.actionSubTypeName = actionSubTypeName;
    }

    public Long getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Long actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public Long getActionStatusId() {
        return actionStatusId;
    }

    public void setActionStatusId(Long actionStatusId) {
        this.actionStatusId = actionStatusId;
    }

    public String getActionStatusName() {
        return actionStatusName;
    }

    public void setActionStatusName(String actionStatusName) {
        this.actionStatusName = actionStatusName;
    }

    public String getParticipantLastName() {
        return participantLastName;
    }

    public void setParticipantLastName(String participantLastName) {
        this.participantLastName = participantLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDTO actionDTO = (ActionDTO) o;
        if (actionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionDTO{" +
            "id=" + getId() +
            ", dueDate='" + getDueDate() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", notes='" + getNotes() + "'" +
            ", participant=" + getParticipantId() +
            ", participant='" + getParticipantFirstName() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", actionSubType=" + getActionSubTypeId() +
            ", actionSubType='" + getActionSubTypeName() + "'" +
            ", actionType=" + getActionTypeId() +
            ", actionType='" + getActionTypeName() + "'" +
            ", actionStatus=" + getActionStatusId() +
            ", actionStatus='" + getActionStatusName() + "'" +
            "}";
    }
}
