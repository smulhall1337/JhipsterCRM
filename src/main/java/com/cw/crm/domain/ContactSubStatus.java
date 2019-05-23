package com.cw.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactSubStatus.
 */
@Entity
@Table(name = "contact_sub_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactSubStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private ContactStatus subTypeOf;

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

    public ContactSubStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactStatus getSubTypeOf() {
        return subTypeOf;
    }

    public ContactSubStatus subTypeOf(ContactStatus contactStatus) {
        this.subTypeOf = contactStatus;
        return this;
    }

    public void setSubTypeOf(ContactStatus contactStatus) {
        this.subTypeOf = contactStatus;
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
        ContactSubStatus contactSubStatus = (ContactSubStatus) o;
        if (contactSubStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactSubStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactSubStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
