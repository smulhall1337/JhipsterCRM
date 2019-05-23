package com.cw.crm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AltContact.
 */
@Entity
@Table(name = "alt_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AltContact implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "relationship", nullable = false)
    private String relationship;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Lob
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    private Participant participant;

    @ManyToOne
    @JsonIgnoreProperties("altContacts")
    private User createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AltContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public AltContact relationship(String relationship) {
        this.relationship = relationship;
        return this;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public AltContact phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public AltContact notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Participant getParticipant() {
        return participant;
    }

    public AltContact participant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public AltContact createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
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
        AltContact altContact = (AltContact) o;
        if (altContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), altContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AltContact{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", relationship='" + getRelationship() + "'" +
            ", phone='" + getPhone() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
