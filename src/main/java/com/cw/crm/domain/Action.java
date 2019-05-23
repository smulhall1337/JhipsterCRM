package com.cw.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "start_date_time")
    private Instant startDateTime;

    @Column(name = "end_date_time")
    private Instant endDateTime;

    @Lob
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Participant participant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ActionSubType actionSubType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ActionType actionType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ActionStatus actionStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Action dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public Action startDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public Action endDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public Action notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Action participant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public User getUser() {
        return user;
    }

    public Action user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActionSubType getActionSubType() {
        return actionSubType;
    }

    public Action actionSubType(ActionSubType actionSubType) {
        this.actionSubType = actionSubType;
        return this;
    }

    public void setActionSubType(ActionSubType actionSubType) {
        this.actionSubType = actionSubType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Action actionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public Action actionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
        return this;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
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
        Action action = (Action) o;
        if (action.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", dueDate='" + getDueDate() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
