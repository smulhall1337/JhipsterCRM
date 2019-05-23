package com.cw.crm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ContactHistory.
 */
@Entity
@Table(name = "contact_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JsonIgnoreProperties("contactHistories")
    private Participant participant;

    @ManyToOne
    @JsonIgnoreProperties("contactHistories")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("contactHistories")
    private ContactType contactType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contactHistories")
    private ContactSubType contactSubType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ContactHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public ContactHistory notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStatus() {
        return status;
    }

    public ContactHistory status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Participant getParticipant() {
        return participant;
    }

    public ContactHistory participant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public User getUser() {
        return user;
    }

    public ContactHistory user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public ContactHistory contactType(ContactType contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public ContactSubType getContactSubType() {
        return contactSubType;
    }

    public ContactHistory contactSubType(ContactSubType contactSubType) {
        this.contactSubType = contactSubType;
        return this;
    }

    public void setContactSubType(ContactSubType contactSubType) {
        this.contactSubType = contactSubType;
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
        ContactHistory contactHistory = (ContactHistory) o;
        if (contactHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
