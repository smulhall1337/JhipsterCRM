package com.cw.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ActionSubType.
 */
@Entity
@Table(name = "action_sub_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionSubType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ActionType subTypeOf;

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

    public ActionSubType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActionType getSubTypeOf() {
        return subTypeOf;
    }

    public ActionSubType subTypeOf(ActionType actionType) {
        this.subTypeOf = actionType;
        return this;
    }

    public void setSubTypeOf(ActionType actionType) {
        this.subTypeOf = actionType;
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
        ActionSubType actionSubType = (ActionSubType) o;
        if (actionSubType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionSubType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionSubType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
